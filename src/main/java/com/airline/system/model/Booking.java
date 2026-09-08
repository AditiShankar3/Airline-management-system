package com.airline.system.model;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    private String flightId;
    private String passengerId;
    private LocalDateTime bookingDate;
    private LocalDateTime travelDate;
    private double totalAmount;
    private int numberOfPassengers;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private String pnr;
    @JsonIgnore
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // The exact Seat IDs assigned to this booking, so cancellation can release
    // precisely these seats instead of only decrementing the flight-level counter.
    @ElementCollection
    @CollectionTable(name = "booking_seat_ids", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_id")
    private List<String> seatIds = new ArrayList<>();
}
