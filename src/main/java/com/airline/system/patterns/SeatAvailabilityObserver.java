package com.airline.system.patterns;

import com.airline.system.model.Booking;
import com.airline.system.service.SeatService;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern — Concrete Observer 2
 * Owner: Alekhya (CS053)
 *
 * Reacts to booking events by updating seat availability on the flight.
 * Decoupled from BookingService — no direct call needed.
 */
@Component
public class SeatAvailabilityObserver implements BookingObserver {

    private final SeatService seatService;

    public SeatAvailabilityObserver(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public void onBookingConfirmed(Booking booking) {
        seatService.decrementAvailableSeats(
            booking.getFlightId(), booking.getNumberOfPassengers());
        System.out.println("[SEATS] Decremented " + booking.getNumberOfPassengers()
            + " seat(s) for flight: " + booking.getFlightId());
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        seatService.incrementAvailableSeats(
            booking.getFlightId(), booking.getNumberOfPassengers());
        System.out.println("[SEATS] Restored " + booking.getNumberOfPassengers()
            + " seat(s) for flight: " + booking.getFlightId());
    }
}
