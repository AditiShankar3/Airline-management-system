package com.airline.system.model;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Aditi (CS029) */
@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    private String flightId;
    private String passengerId;
    private LocalDateTime bookingDate;
    private LocalDateTime travelDate;
    private double totalAmount;
    private int numberOfPassengers;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private String pnr;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
}
