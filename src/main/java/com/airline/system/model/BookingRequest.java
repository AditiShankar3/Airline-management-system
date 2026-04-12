package com.airline.system.model;

import com.airline.system.enums.SeatType;
import lombok.Data;

/**
 * DTO used by BookingController to receive booking request body.
 * Owner: Aditi (CS029)
 */
@Data
public class BookingRequest {
    private String flightId;
    private String passengerId;
    private SeatType seatType;
    private int passengerCount;
}
