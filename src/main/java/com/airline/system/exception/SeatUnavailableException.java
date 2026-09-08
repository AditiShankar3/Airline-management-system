package com.airline.system.exception;

/**
 * Thrown by BookingService when a flight does not have enough available
 * seats to fulfil a booking request.
 *
 * Using a typed exception (instead of RuntimeException) lets BookingController
 * catch it specifically and return HTTP 409 Conflict, which is more correct
 * than a 500 Internal Server Error for a foreseeable business condition.
 *
 * It also makes concurrent double-booking failures legible — when
 * findAvailableSeatsWithLock() returns fewer seats than requested (because a
 * competing transaction just grabbed them), this exception carries the exact
 * flight ID, how many were asked for, and how many were actually left.
 */
public class SeatUnavailableException extends RuntimeException {

    private final String flightId;
    private final int requested;
    private final int available;

    public SeatUnavailableException(String flightId, int requested, int available) {
        super(String.format(
            "Flight %s: requested %d seat(s) but only %d available — please try again.",
            flightId, requested, available
        ));
        this.flightId = flightId;
        this.requested = requested;
        this.available = available;
    }

    public String getFlightId()  { return flightId; }
    public int getRequested()    { return requested; }
    public int getAvailable()    { return available; }
}
