package com.airline.system.controller;

import com.airline.system.model.Booking;
import com.airline.system.model.BookingRequest;
import com.airline.system.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Owner: Aditi (CS029) */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest req) {
        Booking b = bookingService.createBooking(
            req.getFlightId(),
            req.getPassengerId(),
            req.getSeatType(),
            req.getPassengerCount());
        return ResponseEntity.ok(b);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Booking> getHistory(@PathVariable String passengerId) {
        return bookingService.getBookingHistory(passengerId);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled. Refund initiated.");
    }
}
