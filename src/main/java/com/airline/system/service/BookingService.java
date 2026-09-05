package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.*;
import com.airline.system.patterns.BookingBuilder;
import com.airline.system.patterns.BookingObserver;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Owner: Aditi (CS029)
 * SRP: only booking lifecycle — create, confirm, cancel, history.
 * Does NOT handle payments or emails (separate services/observers).
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final List<BookingObserver> observers;

    public BookingService(BookingRepository bookingRepository,
                          SeatService seatService,
                          SeatRepository seatRepository,
                          FlightRepository flightRepository,
                          List<BookingObserver> observers) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.observers = observers;
    }

    /**
     * Creates a booking and marks the chosen seat IDs as unavailable.
     * seatIds: specific seat IDs the passenger selected (from /api/seats/{flightId}).
     *          If empty/null, falls back to auto-assigning first available seats.
     */
    // @Transactional: the booking insert, every seat write, and the observer-driven
    // flight-counter update all commit as one atomic unit. If anything after the
    // booking save throws (e.g. a seat was taken between the availability check and
    // here), the whole operation rolls back instead of leaving a half-booked state.
    @Transactional
    public Booking createBooking(String flightId, String passengerId,
                                  SeatType seatType, int count,
                                  List<String> seatIds) {
        // 1. Validate seat availability
        if (!seatService.hasAvailableSeats(flightId, count))
            throw new RuntimeException("Not enough seats available");

        // 2. Fetch flight to get base price
        Flight flight = flightRepository.findById(flightId)
            .orElseThrow(() -> new RuntimeException("Flight not found"));

        // 3. Build booking with total amount calculated
        Booking booking = new BookingBuilder()
            .withFlight(flightId)
            .withPassenger(passengerId)
            .withSeatType(seatType)
            .withPassengerCount(count)
            .build();
        booking.setTotalAmount(flight.getBasePrice() * count);

        // 4. Save the booking first (to generate ID)
        Booking saved = bookingRepository.save(booking);

        // 5. Mark chosen seats as unavailable, remembering exactly which seats
        //    this booking holds (needed so cancellation can release the same
        //    seats instead of only touching the flight-level counter).
        // Each seat carries a @Version column, so if two requests race for the
        // same seat, the second save() here throws OptimisticLockingFailureException
        // instead of both silently succeeding — that's what closes the double-booking
        // race condition (previously a check-then-act with no protection at all).
        List<String> assignedNums = new ArrayList<>();
        List<String> assignedSeatIds = new ArrayList<>();
        if (seatIds != null && !seatIds.isEmpty()) {
            for (String seatId : seatIds) {
                seatRepository.findById(seatId).ifPresent(seat -> {
                    seat.setAvailable(false);
                    seatRepository.save(seat);
                    assignedNums.add(seat.getSeatNumber());
                    assignedSeatIds.add(seat.getSeatId());
                });
            }
        } else {
            // Auto-assign: pick first N available seats of the right type
            List<Seat> autoSeats = seatRepository
                .findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType)
                .stream().limit(count).toList();
            for (Seat seat : autoSeats) {
                seat.setAvailable(false);
                seatRepository.save(seat);
                assignedNums.add(seat.getSeatNumber());
                assignedSeatIds.add(seat.getSeatId());
            }
        }
        saved.setSeatIds(assignedSeatIds);
        Booking finalBooking = bookingRepository.save(saved);

        // 6. Notify observers. SeatAvailabilityObserver owns the flight-level
        //    seat-count decrement — it used to also be decremented directly here,
        //    which silently drained the counter twice per booking. The counter now
        //    has exactly one owner.
        observers.forEach(o -> o.onBookingConfirmed(finalBooking));

        return finalBooking;
    }

    @Transactional
    public void cancelBooking(String bookingId) {
        Booking b = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus(BookingStatus.CANCELLED);

        // Release the exact seats this booking held (previously only the
        // flight-level counter was restored, leaving these specific seats
        // permanently marked unavailable even though the booking was cancelled).
        if (b.getSeatIds() != null) {
            for (String seatId : b.getSeatIds()) {
                seatRepository.findById(seatId).ifPresent(seat -> {
                    seat.setAvailable(true);
                    seatRepository.save(seat);
                });
            }
        }

        Booking saved = bookingRepository.save(b);
        // Flight-level counter restore is owned solely by the observer (see note above).
        observers.forEach(o -> o.onBookingCancelled(saved));
    }

    public List<Booking> getBookingHistory(String passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    /**
     * Enriched booking list for the passenger "My Bookings" view.
     * Joins Booking + Flight + Seat data into a single DTO per booking.
     */
    public List<BookingDetailDTO> getBookingDetails(String passengerId) {
        List<Booking> bookings = bookingRepository.findByPassengerId(passengerId);
        return bookings.stream().map(b -> {
            BookingDetailDTO dto = new BookingDetailDTO();
            dto.setBookingId(b.getBookingId());
            dto.setPnr(b.getPnr());
            dto.setStatus(b.getStatus());
            dto.setSeatType(b.getSeatType());
            dto.setNumberOfPassengers(b.getNumberOfPassengers());
            dto.setBookingDate(b.getBookingDate());

            // Enrich with flight info
            flightRepository.findById(b.getFlightId()).ifPresent(f -> {
                dto.setFlightNumber(f.getFlightNumber());
                dto.setAirlineName(f.getAirlineName());
                dto.setSource(f.getSource());
                dto.setDestination(f.getDestination());
                dto.setDepartureTime(f.getDepartureTime());
                dto.setArrivalTime(f.getArrivalTime());
            });

            // Collect seat numbers that are booked (unavailable) for this flight
            // and match the passenger count for this booking
            List<Seat> bookedSeats = seatRepository
                .findByFlightFlightIdAndSeatType(b.getFlightId(), b.getSeatType())
                .stream()
                .filter(s -> !s.isAvailable())
                .limit(b.getNumberOfPassengers())
                .toList();

            dto.setSeatNumbers(bookedSeats.stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }

    public List<Booking> getBookingsBetween(LocalDate from, LocalDate to) {
        return bookingRepository.findByBookingDateBetween(
            from.atStartOfDay(), to.atTime(23, 59));
    }

    public List<Booking> getCancelledBetween(LocalDate from, LocalDate to) {
        return bookingRepository.findByBookingDateBetween(
            from.atStartOfDay(), to.atTime(23, 59))
            .stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
            .toList();
    }
    public Booking getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}