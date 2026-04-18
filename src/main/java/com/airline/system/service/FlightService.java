package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.Booking;
import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;
import com.airline.system.model.Seat;
import com.airline.system.patterns.FlightFactory;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public FlightService(FlightRepository flightRepository,
                         BookingRepository bookingRepository,
                         SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Adds a flight AND auto-seeds its seat map so the seat picker works immediately.
     * Layout: rows 1–totalSeats/6, columns A–F.
     *   Rows 1–2         → FIRST_CLASS   (cols A–D, 4 per row)
     *   Rows 3–(n/3)     → BUSINESS      (cols A–F, 6 per row)
     *   Remaining rows   → ECONOMY       (cols A–F, 6 per row)
     */
    public Flight addFlight(FlightRequest request) {
        Flight flight = FlightFactory.createFlight(request);
        Flight saved = flightRepository.save(flight);
        seedSeats(saved);
        return saved;
    }

    private void seedSeats(Flight flight) {
        int total = flight.getTotalSeats() > 0 ? flight.getTotalSeats() : 180;
        List<Seat> seats = new ArrayList<>();

        // First class: rows 1–2, cols A-D  (8 seats)
        for (int row = 1; row <= 2; row++) {
            for (char col : new char[]{'A','B','C','D'}) {
                seats.add(makeSeat(flight, row, col, SeatType.FIRST_CLASS));
            }
        }
        // Business: rows 3–(firstClassRows + businessRows), cols A-F
        int businessRows = Math.max(2, total / 20);
        for (int row = 3; row < 3 + businessRows; row++) {
            for (char col : new char[]{'A','B','C','D','E','F'}) {
                seats.add(makeSeat(flight, row, col, SeatType.BUSINESS));
            }
        }
        // Economy: fill remaining
        int ecoStart = 3 + businessRows;
        int ecoRows = Math.max(5, (total - seats.size()) / 6);
        for (int row = ecoStart; row < ecoStart + ecoRows; row++) {
            for (char col : new char[]{'A','B','C','D','E','F'}) {
                seats.add(makeSeat(flight, row, col, SeatType.ECONOMY));
            }
        }

        seatRepository.saveAll(seats);
    }

    private Seat makeSeat(Flight flight, int row, char col, SeatType type) {
        Seat s = new Seat();
        s.setSeatNumber(row + String.valueOf(col));
        s.setRow(row);
        s.setSeatColumn(col);
        s.setSeatType(type);
        s.setAvailable(true);
        s.setFlight(flight);
        s.setPrice(switch (type) {
            case FIRST_CLASS -> flight.getBasePrice() * 3;
            case BUSINESS    -> flight.getBasePrice() * 1.8;
            case ECONOMY     -> flight.getBasePrice();
        });
        return s;
    }

    public Flight updateFlight(String flightId, Flight details) {
        Flight f = flightRepository.findById(flightId)
            .orElseThrow(() -> new RuntimeException("Flight not found"));
        f.setFlightNumber(details.getFlightNumber());
        f.setAirlineName(details.getAirlineName());
        f.setDepartureTime(details.getDepartureTime());
        f.setBasePrice(details.getBasePrice());
        return flightRepository.save(f);
    }

    /** Cancel all active bookings before deleting the flight */
    public void deleteFlight(String flightId) {
        bookingRepository.findByFlightId(flightId).forEach(b -> {
            if (b.getStatus() != BookingStatus.CANCELLED) {
                b.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(b);
            }
        });
        seatRepository.deleteAll(seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId));
        flightRepository.deleteById(flightId);
    }

    public List<Flight> searchFlights(String src, String dest, java.time.LocalDateTime date) {
        if (src == null || src.isBlank()) return flightRepository.findAll();
        return flightRepository.findBySourceAndDestinationAndDepartureTimeBetween(
            src, dest, date, date.plusDays(1));
    }

    public List<Flight> getFlightsBetween(LocalDate from, LocalDate to) {
        return flightRepository.findAll().stream()
            .filter(f -> f.getDepartureTime() != null
                && !f.getDepartureTime().toLocalDate().isBefore(from)
                && !f.getDepartureTime().toLocalDate().isAfter(to))
            .toList();
    }
}