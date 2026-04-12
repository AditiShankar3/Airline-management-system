package com.airline.system.patterns;

import com.airline.system.model.Booking;
import com.airline.system.model.Flight;
import com.airline.system.model.Payment;
import com.airline.system.service.BookingService;
import com.airline.system.service.FlightService;
import com.airline.system.service.PaymentService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facade Pattern — Structural
 * Owner: 4th Member (PES1UG23CS___)
 *
 * Problem solved: Admin report needs data from 3 services. Without Facade,
 * the controller would call all 3 directly (tight coupling). Facade hides
 * that complexity behind a single clean method.
 *
 * DIP: depends on service abstractions, not concretions.
 */
@Component
public class ReportFacade {

    private final BookingService bookingService;
    private final FlightService flightService;
    private final PaymentService paymentService;

    public ReportFacade(BookingService bookingService,
                        FlightService flightService,
                        PaymentService paymentService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.paymentService = paymentService;
    }

    public Map<String, Object> generateRevenueReport(LocalDate from, LocalDate to) {
        List<Booking> bookings = bookingService.getBookingsBetween(from, to);
        List<Payment> payments = paymentService.getPaymentsBetween(from, to);
        List<Flight> flights = flightService.getFlightsBetween(from, to);

        double totalRevenue = payments.stream()
            .mapToDouble(Payment::getAmount)
            .sum();

        Map<String, Object> report = new HashMap<>();
        report.put("from", from);
        report.put("to", to);
        report.put("totalBookings", bookings.size());
        report.put("totalRevenue", totalRevenue);
        report.put("totalFlights", flights.size());
        report.put("bookings", bookings);
        report.put("payments", payments);
        return report;
    }

    public Map<String, Object> generateCancellationReport(LocalDate from, LocalDate to) {
        List<Booking> cancelled = bookingService.getCancelledBetween(from, to);

        Map<String, Object> report = new HashMap<>();
        report.put("from", from);
        report.put("to", to);
        report.put("totalCancellations", cancelled.size());
        report.put("cancelledBookings", cancelled);
        return report;
    }
}
