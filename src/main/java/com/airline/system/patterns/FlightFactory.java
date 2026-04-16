package com.airline.system.patterns;

import com.airline.system.enums.FlightStatus;
import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;

import java.time.Duration;
import java.util.Map;

/**
 * Factory Pattern — Creational
 * Owner: Alekhya (CS053)
 *
 * Problem solved: Without Factory, every endpoint / service that creates a
 * Flight would duplicate field-mapping and type-selection logic.  FlightFactory
 * centralises construction and delegates type-specific rules to FlightType
 * implementations, honouring both the Factory Pattern and OCP.
 *
 * Adding a new flight type (e.g. "CHARTER") only requires:
 *   1. A new FlightType implementation.
 *   2. One line in the FLIGHT_TYPES map below.
 *   No other class needs to change.
 */
public class FlightFactory {

    /**
     * Registry mapping type strings to their FlightType strategy.
     * Using a Map instead of if-else eliminates branching and makes the
     * factory trivially extensible (OCP).
     */
    private static final Map<String, FlightType> FLIGHT_TYPES = Map.of(
            "DOMESTIC",      new DomesticFlight(),
            "INTERNATIONAL", new InternationalFlight()
    );

    /**
     * Build and return a fully initialised Flight entity from a FlightRequest.
     *
     * @param request incoming DTO carrying all user-supplied fields
     * @return a ready-to-persist Flight entity with type rules applied
     * @throws IllegalArgumentException if the requested type is unknown
     */
    public static Flight createFlight(FlightRequest request) {

        // ── 1. Resolve flight type strategy ──────────────────────────────────
        String typeKey = (request.getType() == null) ? "" : request.getType().toUpperCase();
        FlightType flightType = FLIGHT_TYPES.get(typeKey);
        if (flightType == null) {
            throw new IllegalArgumentException(
                    "Unknown flight type: '" + request.getType() +
                    "'. Accepted values: " + FLIGHT_TYPES.keySet());
        }

        // ── 2. Map DTO → entity ───────────────────────────────────────────────
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAirlineName(request.getAirlineName());
        flight.setSource(request.getSource());
        flight.setDestination(request.getDestination());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setAvailableSeats(request.getTotalSeats());   // all seats open on creation
        flight.setBasePrice(request.getBasePrice());
        flight.setStatus(FlightStatus.SCHEDULED);

        // ── 3. Duration (optional enhancement) ───────────────────────────────
        if (request.getDepartureTime() != null && request.getArrivalTime() != null) {
            int minutes = (int) Duration.between(
                    request.getDepartureTime(), request.getArrivalTime()).toMinutes();
            flight.setDuration(minutes);
        }

        // ── 4. Apply type-specific rules via polymorphism ─────────────────────
        flightType.applyRules(flight);

        return flight;
    }
}
