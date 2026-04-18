package com.airline.system.model;

import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

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

    @Column(name = "seat_row")      // was row_number, now seat_row
    private int row;

    @Column(name = "seat_column")
    private char seatColumn;

    private double price;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}