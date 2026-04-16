package com.airline.system.model;

import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

/** Owner: Aditi (CS029) */
@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String seatId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private boolean isAvailable = true;
    private int row;
    @Column(name = "seat_column")
    private char column;
    private double price;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
