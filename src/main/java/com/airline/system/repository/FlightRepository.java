package com.airline.system.repository;

import com.airline.system.enums.FlightStatus;
import com.airline.system.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findBySourceAndDestinationAndDepartureTimeBetween(
        String source, String destination,
        LocalDateTime from, LocalDateTime to);
    List<Flight> findByStatus(FlightStatus status);
}
