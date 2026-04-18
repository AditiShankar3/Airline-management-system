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

    public List<Seat> getAllSeats(String flightId) {
        return seatRepository.findByFlightFlightId(flightId);
    }

    public List<Seat> getAvailableSeats(String flightId) {
        return seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId);
    }

    public List<Seat> getSeatsByType(String flightId, SeatType seatType) {
        return seatRepository.findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType);
    }

    public boolean hasAvailableSeats(String flightId, int requiredCount) {
        return flightRepository.findById(flightId)
            .map(f -> f.getAvailableSeats() >= requiredCount)
            .orElse(false);
    }

    public void decrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(f -> {
            f.setAvailableSeats(Math.max(0, f.getAvailableSeats() - count));
            flightRepository.save(f);
        });
    }

    public void incrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(f -> {
            f.setAvailableSeats(f.getAvailableSeats() + count);
            flightRepository.save(f);
        });
    }
}