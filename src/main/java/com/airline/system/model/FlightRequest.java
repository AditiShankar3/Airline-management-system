package com.airline.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO for creating a new Flight.
 * Decouples the HTTP request payload from the JPA entity.
 * Owner: Alekhya (CS053)
 */
public class FlightRequest {

    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private double basePrice;

    /**
     * Flight type discriminator: "DOMESTIC" or "INTERNATIONAL".
     * Used by FlightFactory to select the correct FlightType strategy (OCP).
     */
    private String type;

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getFlightNumber()             { return flightNumber; }
    public void   setFlightNumber(String v)     { this.flightNumber = v; }

    public String getAirlineName()              { return airlineName; }
    public void   setAirlineName(String v)      { this.airlineName = v; }

    public String getSource()                   { return source; }
    public void   setSource(String v)           { this.source = v; }

    public String getDestination()              { return destination; }
    public void   setDestination(String v)      { this.destination = v; }

    public LocalDateTime getDepartureTime()     { return departureTime; }
    public void   setDepartureTime(LocalDateTime v) { this.departureTime = v; }

    public LocalDateTime getArrivalTime()       { return arrivalTime; }
    public void   setArrivalTime(LocalDateTime v)   { this.arrivalTime = v; }

    public int    getTotalSeats()               { return totalSeats; }
    public void   setTotalSeats(int v)          { this.totalSeats = v; }

    public double getBasePrice()                { return basePrice; }
    public void   setBasePrice(double v)        { this.basePrice = v; }

    public String getType()                     { return type; }
    public void   setType(String v)             { this.type = v; }
}
