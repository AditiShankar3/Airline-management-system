package com.airline.system.repository;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/** Owner: Aditi (CS029) */
@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByFlightFlightId(String flightId);   // all seats (taken + available)
    List<Seat> findByFlightFlightIdAndIsAvailableTrue(String flightId);
    List<Seat> findByFlightFlightIdAndSeatType(String flightId, SeatType seatType);
    List<Seat> findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(String flightId, SeatType seatType);
}