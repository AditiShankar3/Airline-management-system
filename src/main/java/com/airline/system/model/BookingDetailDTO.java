package com.airline.system.model;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO: enriched booking info for passenger view.
 * Merges Booking + Flight + Seat numbers so frontend gets
 * everything it needs in one call.
 * Owner: Aditi (CS029)
 */
@Data
public class BookingDetailDTO {
    private String bookingId;
    private String pnr;
    private BookingStatus status;
    private SeatType seatType;
    private int numberOfPassengers;
    private LocalDateTime bookingDate;

    // from Flight
    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    // seat numbers assigned to this booking
    private List<String> seatNumbers;
}