package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.PaymentMethod;
import com.airline.system.enums.PaymentStatus;
import com.airline.system.model.Booking;
import com.airline.system.model.Payment;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository; // ✅ ADD THIS

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository) { // ✅ ADD THIS
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository; // ✅ ADD THIS
    }

    public Payment processPayment(String bookingId, double amount, PaymentMethod method) {
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        payment.setStatus(PaymentStatus.SUCCESS);
        Payment saved = paymentRepository.save(payment);

        // ✅ UPDATE BOOKING STATUS TO CONFIRMED
        bookingRepository.findById(bookingId).ifPresent(booking -> {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        });

        return saved;
    }

    public Payment refundPayment(String bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("Payment not found for booking: " + bookingId));
        payment.setStatus(PaymentStatus.REFUNDED);
        Payment saved = paymentRepository.save(payment);

        // ✅ UPDATE BOOKING STATUS TO CANCELLED ON REFUND
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