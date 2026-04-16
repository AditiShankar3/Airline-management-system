package com.airline.system.service;

import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;
import com.airline.system.patterns.FlightFactory;
import com.airline.system.repository.FlightRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * Factory Pattern entry-point.
     * Delegates object construction to FlightFactory so the service never
     * calls {@code new Flight()} directly and remains unaware of type rules.
     */
    public Flight addFlight(FlightRequest request) {
        Flight flight = FlightFactory.createFlight(request);
        return flightRepository.save(flight);
    }

    public Flight updateFlight(String flightId, Flight details) {
        Flight f = flightRepository.findById(flightId)
            .orElseThrow(() -> new RuntimeException("Flight not found"));
        f.setFlightNumber(details.getFlightNumber());
        f.setAirlineName(details.getAirlineName());
        f.setDepartureTime(details.getDepartureTime());
        f.setBasePrice(details.getBasePrice());
        return flightRepository.save(f);
    }

    public void deleteFlight(String flightId) {
        flightRepository.deleteById(flightId);
    }

    public List<Flight> searchFlights(String src, String dest, LocalDateTime date) {
        if (src == null) return flightRepository.findAll();
        return flightRepository.findBySourceAndDestinationAndDepartureTimeBetween(
            src, dest, date, date.plusDays(1));
    }

    public List<Flight> getFlightsBetween(LocalDate from, LocalDate to) {
        return flightRepository.findAll().stream()
            .filter(f -> f.getDepartureTime() != null
                && !f.getDepartureTime().toLocalDate().isBefore(from)
                && !f.getDepartureTime().toLocalDate().isAfter(to))
            .toList();
    }
}

