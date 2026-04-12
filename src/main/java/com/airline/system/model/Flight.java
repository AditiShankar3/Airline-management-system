package com.airline.system.model;

import com.airline.system.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String flightId;

    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int duration;
    private int totalSeats;
    private int availableSeats;
    private double basePrice;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.SCHEDULED;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}
