package com.airline.system.controller;

import com.airline.system.enums.PaymentMethod;
import com.airline.system.model.Payment;
import com.airline.system.service.PaymentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Owner: Aditi (CS029) */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody PaymentRequest req) {
        Payment p = paymentService.processPayment(
            req.getBookingId(), req.getAmount(), req.getPaymentMethod());
        return ResponseEntity.ok(p);
    }

    @PutMapping("/refund/{bookingId}")
    public ResponseEntity<Payment> refundPayment(@PathVariable String bookingId) {
        return ResponseEntity.ok(paymentService.refundPayment(bookingId));
    }

    // Inner DTO — keeps it simple, no extra file needed
    @Data
    static class PaymentRequest {
        private String bookingId;
        private double amount;
        private PaymentMethod paymentMethod;
    }
}
