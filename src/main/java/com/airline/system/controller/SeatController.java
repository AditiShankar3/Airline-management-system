package com.airline.system.controller;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import com.airline.system.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Owner: Aditi (CS029) */
@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{flightId}")
    public List<Seat> getAvailableSeats(@PathVariable String flightId) {
        return seatService.getAvailableSeats(flightId);
    }

    @GetMapping("/{flightId}/type")
    public List<Seat> getSeatsByType(@PathVariable String flightId,
                                      @RequestParam SeatType seatType) {
        return seatService.getSeatsByType(flightId, seatType);
    }
}
