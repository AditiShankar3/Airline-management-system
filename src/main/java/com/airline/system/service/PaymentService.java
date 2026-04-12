package com.airline.system.service;

import com.airline.system.enums.PaymentMethod;
import com.airline.system.enums.PaymentStatus;
import com.airline.system.model.Payment;
import com.airline.system.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Owner: Aditi (CS029)
 * SRP: only payment processing — process, validate, refund.
 * Changes ONLY if payment rules change (e.g. new payment method).
 */
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(String bookingId, double amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        payment.setStatus(PaymentStatus.SUCCESS); // Simplified: always succeeds
        return paymentRepository.save(payment);
    }

    public Payment refundPayment(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("Payment not found for booking: " + bookingId));
        payment.setStatus(PaymentStatus.REFUNDED);
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsBetween(LocalDate from, LocalDate to) {
        return paymentRepository.findByPaymentDateBetween(
            from.atStartOfDay(), to.atTime(23, 59));
    }
}
