package com.airline.system.patterns;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.Booking;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Builder Pattern — Creational
 * Owner: Aditi (CS029)
 *
 * Problem solved: Booking has many fields. Without Builder, a 10-parameter
 * constructor is needed. Builder lets each step of the booking flow add one
 * field at a time before calling build().
 */
public class BookingBuilder {

    private String flightId;
    private String passengerId;
    private SeatType seatType;
    private int numPassengers;

    public BookingBuilder withFlight(String flightId) {
        this.flightId = flightId;
        return this;
    }

    public BookingBuilder withPassenger(String passengerId) {
        this.passengerId = passengerId;
        return this;
    }

    public BookingBuilder withSeatType(SeatType seatType) {
        this.seatType = seatType;
        return this;
    }

    public BookingBuilder withPassengerCount(int count) {
        this.numPassengers = count;
        return this;
    }

    public Booking build() {
        Booking b = new Booking();
        b.setFlightId(flightId);
        b.setPassengerId(passengerId);
        b.setSeatType(seatType);
        b.setNumberOfPassengers(numPassengers);
        b.setBookingDate(LocalDateTime.now());
        b.setStatus(BookingStatus.PENDING);
        b.setPnr(generatePNR());
        return b;
    }

    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
