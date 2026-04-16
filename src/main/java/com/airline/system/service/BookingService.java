package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.Booking;
import com.airline.system.patterns.BookingBuilder;
import com.airline.system.patterns.BookingObserver;
import com.airline.system.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

/**
 * Owner: Aditi (CS029)
 * SRP: only booking lifecycle — create, confirm, cancel, history.
 * Does NOT handle payments or emails (separate services/observers).
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final List<BookingObserver> observers; // Spring injects all @Component observers

    public BookingService(BookingRepository bookingRepository,
                          SeatService seatService,
                          List<BookingObserver> observers) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.observers = observers;
    }

    public Booking createBooking(String flightId, String passengerId,
                                  SeatType seatType, int count) {
        if (!seatService.hasAvailableSeats(flightId, count))
            throw new RuntimeException("Not enough seats available");

        Booking booking = new BookingBuilder()
            .withFlight(flightId)
            .withPassenger(passengerId)
            .withSeatType(seatType)
            .withPassengerCount(count)
            .build();

        Booking saved = bookingRepository.save(booking);
        observers.forEach(o -> o.onBookingConfirmed(saved)); // Observer pattern
        return saved;
    }

    public void cancelBooking(String bookingId) {
        Booking b = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus(BookingStatus.CANCELLED);
        Booking saved = bookingRepository.save(b);
        observers.forEach(o -> o.onBookingCancelled(saved));
    }

    public List<Booking> getBookingHistory(String passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
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
}
