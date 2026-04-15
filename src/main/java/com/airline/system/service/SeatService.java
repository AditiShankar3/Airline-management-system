package com.airline.system.service;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Owner: Aditi (CS029)
 * SRP: only responsible for seat availability logic.
 * Called by BookingService and SeatAvailabilityObserver.
 */
@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public List<Seat> getAvailableSeats(String flightId) {
        return seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId);
    }

    public List<Seat> getSeatsByType(String flightId, SeatType seatType) {
        return seatRepository.findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType);
    }

    public boolean hasAvailableSeats(String flightId, int requiredCount) {
        List<Seat> availableSeats = seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId);
        if (!availableSeats.isEmpty()) {
            return availableSeats.size() >= requiredCount;
        }
        // Fallback: use flight-level counter for simple demo flow
        return flightRepository.findById(flightId)
            .map(f -> f.getAvailableSeats() >= requiredCount)
            .orElse(false);
    }

    public void decrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(flight -> {
            flight.setAvailableSeats(flight.getAvailableSeats() - count);
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
