package com.airline.system.service;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public List<Seat> getAvailableSeats(String flightId) {
        try {
            return seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId);
        } catch (Exception e) {
            return List.of();
        }
    }

    public List<Seat> getSeatsByType(String flightId, SeatType seatType) {
        try {
            return seatRepository.findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType);
        } catch (Exception e) {
            return List.of();
        }
    }

    public boolean hasAvailableSeats(String flightId, int requiredCount) {
        // Always use flight-level counter — simpler and more reliable for demo
        return flightRepository.findById(flightId)
            .map(f -> f.getAvailableSeats() >= requiredCount)
            .orElse(false);
    }

    public void decrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(flight -> {
            flight.setAvailableSeats(Math.max(0, flight.getAvailableSeats() - count));
            flightRepository.save(flight);
        });
    }

    public void incrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(flight -> {
            flight.setAvailableSeats(flight.getAvailableSeats() + count);
            flightRepository.save(flight);
        });
    }
}