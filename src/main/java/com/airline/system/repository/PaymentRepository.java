package com.airline.system.repository;

import com.airline.system.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/** Owner: Aditi (CS029) */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByPaymentDateBetween(LocalDateTime from, LocalDateTime to);
    Optional<Payment> findByBookingId(String bookingId);
}
