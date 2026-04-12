package com.airline.system.repository;

import com.airline.system.enums.BookingStatus;
import com.airline.system.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Aditi (CS029) */
@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByPassengerId(String passengerId);
    List<Booking> findByStatus(BookingStatus status);
    List<Booking> findByBookingDateBetween(LocalDateTime from, LocalDateTime to);
}
