package com.airline.system.model;

import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

/** Owner: Aditi (CS029) */
@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;

    private String ticketNumber;
    private String passengerName;
    private int age;
    private String gender;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private double price;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
