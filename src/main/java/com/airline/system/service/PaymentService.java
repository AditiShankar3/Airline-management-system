package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.PaymentMethod;
import com.airline.system.enums.PaymentStatus;
import com.airline.system.model.Booking;
import com.airline.system.model.Payment;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
    }

    /**
     * Concurrency note: @Transactional(rollbackFor) wraps the payment save +
     * booking status update as one atomic unit. Without this, if the payment
     * record is saved but the booking update throws, the DB is left in an
     * inconsistent state (payment SUCCESS, booking still PENDING).
     */
    @Transactional(rollbackFor = Exception.class)
    public Payment processPayment(String bookingId, double amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment saved = paymentRepository.save(payment);

        // ✅ UPDATE BOOKING STATUS TO CONFIRMED — same transaction as payment save
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        });

        return saved;
    }

    /**
     * Concurrency note: same reasoning — refund record save + booking status
     * update must be atomic. If one fails, both roll back.
     */
    @Transactional(rollbackFor = Exception.class)
    public Payment refundPayment(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("Payment not found for booking: " + bookingId));
        payment.setStatus(PaymentStatus.REFUNDED);
        Payment saved = paymentRepository.save(payment);

        // ✅ UPDATE BOOKING STATUS TO CANCELLED ON REFUND — same transaction
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            booking.setStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
        });

        return saved;
    }

    public List<Payment> getPaymentsBetween(LocalDate from, LocalDate to) {
        return paymentRepository.findByPaymentDateBetween(
            from.atStartOfDay(), to.atTime(23, 59));
    }
}