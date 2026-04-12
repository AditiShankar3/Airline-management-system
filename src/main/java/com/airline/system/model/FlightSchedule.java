package com.airline.system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Owner: Alekhya (CS053)
 * Represents a scheduled instance of a flight (recurring schedules).
 */
@Entity
@Data
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String scheduleId;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private LocalDateTime scheduledDeparture;
    private LocalDateTime scheduledArrival;
    private String gateNumber;
    private String terminal;
}
