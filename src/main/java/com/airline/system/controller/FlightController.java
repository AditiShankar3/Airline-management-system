package com.airline.system.controller;

import com.airline.system.model.Flight;
import com.airline.system.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/** Owner: Alekhya (CS053) */
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.searchFlights(null, null, null);
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String src,
                                       @RequestParam String dest,
                                       @RequestParam String date) {
        return flightService.searchFlights(src, dest, LocalDateTime.parse(date));
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.addFlight(flight));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable String id,
                                                @RequestBody Flight details) {
        return ResponseEntity.ok(flightService.updateFlight(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted");
    }
}
