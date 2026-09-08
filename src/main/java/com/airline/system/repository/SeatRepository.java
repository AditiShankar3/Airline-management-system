package com.airline.system.repository;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/** Owner: Aditi (CS029) */
@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByFlightFlightId(String flightId);   // all seats (taken + available)
    List<Seat> findByFlightFlightIdAndIsAvailableTrue(String flightId);
    List<Seat> findByFlightFlightIdAndSeatType(String flightId, SeatType seatType);
    List<Seat> findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(String flightId, SeatType seatType);

    /**
     * Pessimistic locking — Concurrency Control.
     *
     * Issues a SELECT ... FOR UPDATE on the matching seat rows.
     * When Transaction A calls this, MySQL acquires an exclusive row lock.
     * Transaction B calling the same method for the same flight blocks at the
     * database level until A commits or rolls back. This closes the classic
     * "check-then-act" race condition where two threads both read N seats
     * available and both proceed to book — one of them would have overbooked.
     *
     * MUST be called inside a @Transactional method; the lock is held for the
     * duration of that transaction and released on commit/rollback.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.flight.flightId = :flightId " +
           "AND s.seatType = :seatType AND s.isAvailable = true")
    List<Seat> findAvailableSeatsWithLock(
        @Param("flightId") String flightId,
        @Param("seatType") SeatType seatType
    );
}