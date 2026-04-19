This file is a merged representation of the entire codebase, combined into a single document by Repomix.

# File Summary

## Purpose
This file contains a packed representation of the entire repository's contents.
It is designed to be easily consumable by AI systems for analysis, code review,
or other automated processes.

## File Format
The content is organized as follows:
1. This summary section
2. Repository information
3. Directory structure
4. Repository files (if enabled)
5. Multiple file entries, each consisting of:
  a. A header with the file path (## File: path/to/file)
  b. The full contents of the file in a code block

## Usage Guidelines
- This file should be treated as read-only. Any changes should be made to the
  original repository files, not this packed version.
- When processing this file, use the file path to distinguish
  between different files in the repository.
- Be aware that this file may contain sensitive information. Handle it with
  the same level of security as you would the original repository.

## Notes
- Some files may have been excluded based on .gitignore rules and Repomix's configuration
- Binary files are not included in this packed representation. Please refer to the Repository Structure section for a complete list of file paths, including binary files
- Files matching patterns in .gitignore are excluded
- Files matching default ignore patterns are excluded
- Files are sorted by Git change count (files with more changes are at the bottom)

# Directory Structure
```
src/
  main/
    java/
      com/
        airline/
          system/
            config/
              CorsConfig.java
              DatabaseConfig.java
              SecurityConfig.java
            controller/
              AuthController.java
              BookingController.java
              FlightController.java
              PaymentController.java
              ReportController.java
              SeatController.java
            enums/
              BookingStatus.java
              FlightStatus.java
              PaymentMethod.java
              PaymentStatus.java
              SeatType.java
              UserRole.java
            model/
              Administrator.java
              Booking.java
              BookingDetailDTO.java
              BookingRequest.java
              Flight.java
              FlightRequest.java
              FlightSchedule.java
              Passenger.java
              Payment.java
              Report.java
              Seat.java
              Staff.java
              Ticket.java
              User.java
            patterns/
              BookingBuilder.java
              BookingObserver.java
              DomesticFlight.java
              EmailNotificationObserver.java
              FlightFactory.java
              FlightType.java
              InternationalFlight.java
              ReportFacade.java
              SeatAvailabilityObserver.java
              UserFactory.java
            repository/
              BookingRepository.java
              FlightRepository.java
              PaymentRepository.java
              ReportRepository.java
              SeatRepository.java
              UserRepository.java
            service/
              BookingService.java
              FlightService.java
              PaymentService.java
              ReportService.java
              SeatService.java
              UserService.java
            SystemApplication.java
    resources/
      static/
        index.html
      application.properties
.gitignore
docker-compose.yml
package.json
pom.xml
README.md
```

# Files

## File: src/main/java/com/airline/system/config/CorsConfig.java
````java
package com.airline.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
````

## File: src/main/java/com/airline/system/config/DatabaseConfig.java
````java
package com.airline.system.config;

import org.springframework.context.annotation.Configuration;

/**
 * Singleton Pattern — Owner: 4th Member (PES1UG23CS___)
 * Spring Boot auto-configures the DataSource from application.properties.
 * TODO (4th Member): Add any custom DataSource bean, connection pool tuning, or
 * explicit Singleton pattern demonstration here as required by the report.
 */
@Configuration
public class DatabaseConfig {
    // Full implementation: 4th Member
}
````

## File: src/main/java/com/airline/system/config/SecurityConfig.java
````java
package com.airline.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * STARTER SECURITY CONFIG — permits all requests so teammates can test APIs immediately.
 * TODO (Pranav - CS002): Replace this entire class with JWT-based role security.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // TODO: Pranav — replace with JWT + role-based rules
            );
        return http.build();
    }
}
````

## File: src/main/java/com/airline/system/controller/PaymentController.java
````java
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
````

## File: src/main/java/com/airline/system/controller/ReportController.java
````java
package com.airline.system.controller;

import com.airline.system.patterns.ReportFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/** Owner: 4th Member (PES1UG23CS___) */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportFacade reportFacade;

    public ReportController(ReportFacade reportFacade) {
        this.reportFacade = reportFacade;
    }

    @GetMapping("/revenue")
    public ResponseEntity<Map<String, Object>> revenue(@RequestParam String from,
                                                        @RequestParam String to) {
        return ResponseEntity.ok(
            reportFacade.generateRevenueReport(LocalDate.parse(from), LocalDate.parse(to)));
    }

    @GetMapping("/cancellations")
    public ResponseEntity<Map<String, Object>> cancellations(@RequestParam String from,
                                                              @RequestParam String to) {
        return ResponseEntity.ok(
            reportFacade.generateCancellationReport(LocalDate.parse(from), LocalDate.parse(to)));
    }
}
````

## File: src/main/java/com/airline/system/enums/BookingStatus.java
````java
package com.airline.system.enums;

public enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}
````

## File: src/main/java/com/airline/system/enums/FlightStatus.java
````java
package com.airline.system.enums;

public enum FlightStatus {
    SCHEDULED, DELAYED, CANCELLED, COMPLETED
}
````

## File: src/main/java/com/airline/system/enums/PaymentMethod.java
````java
package com.airline.system.enums;

public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING
}
````

## File: src/main/java/com/airline/system/enums/PaymentStatus.java
````java
package com.airline.system.enums;

public enum PaymentStatus {
    PENDING, SUCCESS, FAILED, REFUNDED
}
````

## File: src/main/java/com/airline/system/enums/SeatType.java
````java
package com.airline.system.enums;

public enum SeatType {
    ECONOMY, BUSINESS, FIRST_CLASS
}
````

## File: src/main/java/com/airline/system/enums/UserRole.java
````java
package com.airline.system.enums;

public enum UserRole {
    PASSENGER, STAFF, ADMIN
}
````

## File: src/main/java/com/airline/system/model/Administrator.java
````java
package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends User {

    private String adminId;
    private int accessLevel;

    @Override
    public boolean login() { return isActive() && accessLevel > 0; }

    @Override
    public void logout() { }
}
````

## File: src/main/java/com/airline/system/model/BookingDetailDTO.java
````java
package com.airline.system.model;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO: enriched booking info for passenger view.
 * Merges Booking + Flight + Seat numbers so frontend gets
 * everything it needs in one call.
 * Owner: Aditi (CS029)
 */
@Data
public class BookingDetailDTO {
    private String bookingId;
    private String pnr;
    private BookingStatus status;
    private SeatType seatType;
    private int numberOfPassengers;
    private LocalDateTime bookingDate;

    // from Flight
    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    // seat numbers assigned to this booking
    private List<String> seatNumbers;
}
````

## File: src/main/java/com/airline/system/model/FlightSchedule.java
````java
package com.airline.system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Owner: Alekhya (CS053)
 * Represents a scheduled instance of a flight (recurring schedules).
 */
@Entity
@Data
public class FlightSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String scheduleId;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private LocalDateTime scheduledDeparture;
    private LocalDateTime scheduledArrival;
    private String gateNumber;
    private String terminal;
}
````

## File: src/main/java/com/airline/system/model/Passenger.java
````java
package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Passenger extends User {

    private String passportNumber;
    private LocalDate dateOfBirth;
    private String address;

    @Override
    public boolean login() { return isActive(); }

    @Override
    public void logout() { /* clear session */ }
}
````

## File: src/main/java/com/airline/system/model/Payment.java
````java
package com.airline.system.model;

import com.airline.system.enums.PaymentMethod;
import com.airline.system.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/** Owner: Aditi (CS029) */
@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    private String bookingId;
    private double amount;
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String transactionId;
}
````

## File: src/main/java/com/airline/system/model/Report.java
````java
package com.airline.system.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Owner: 4th Member (PES1UG23CS___)
 * Represents a generated admin report persisted to DB.
 */
@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportId;

    private String reportType;       // e.g. "REVENUE", "CANCELLATION"
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime generatedAt = LocalDateTime.now();
    private String generatedBy;      // admin userId
    private double totalRevenue;
    private int totalBookings;
    private int totalCancellations;
}
````

## File: src/main/java/com/airline/system/model/Staff.java
````java
package com.airline.system.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/** Owner: Pranav (CS002) */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Staff extends User {

    private String staffId;
    private String designation;
    private String department;

    @Override
    public boolean login() { return isActive(); }

    @Override
    public void logout() { }
}
````

## File: src/main/java/com/airline/system/model/Ticket.java
````java
package com.airline.system.model;

import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

/** Owner: Aditi (CS029) */
@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;

    private String ticketNumber;
    private String passengerName;
    private int age;
    private String gender;
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private double price;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
````

## File: src/main/java/com/airline/system/model/User.java
````java
package com.airline.system.model;

import com.airline.system.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Abstract base entity — Owner: Pranav (CS002)
 * LSP: all subtypes (Passenger, Staff, Administrator) are fully substitutable.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String username;
    private String password;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean isActive = true;

    public abstract boolean login();   // LSP: every subtype must implement meaningfully
    public abstract void logout();
}
````

## File: src/main/java/com/airline/system/patterns/BookingObserver.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Booking;

/**
 * Observer Pattern — interface for all booking event listeners.
 * Owner: Pranav (CS002) + 4th Member
 */
public interface BookingObserver {
    void onBookingConfirmed(Booking booking);
    void onBookingCancelled(Booking booking);
}
````

## File: src/main/java/com/airline/system/patterns/DomesticFlight.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * OCP Implementation — Domestic flight rules.
 *
 * Domestic flights operate within the same country; no price adjustment is
 * needed beyond the base price set by the operator.
 *
 * Owner: Alekhya (CS053)
 */
public class DomesticFlight implements FlightType {

    @Override
    public void applyRules(Flight flight) {
        // No price adjustment for domestic routes.
        // Additional domestic-specific rules can be added here without
        // touching FlightFactory or any other class (OCP).
    }
}
````

## File: src/main/java/com/airline/system/patterns/EmailNotificationObserver.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Booking;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern — Concrete Observer 1
 * Owner: Pranav (CS002)
 *
 * Reacts to booking events by sending email notifications.
 * Decoupled from BookingService — BookingService doesn't know this class exists.
 */
@Component
public class EmailNotificationObserver implements BookingObserver {

    @Override
    public void onBookingConfirmed(Booking booking) {
        // TODO: integrate JavaMail or SendGrid for production
        System.out.println("[EMAIL] Booking confirmation sent for PNR: " + booking.getPnr()
            + " | Passenger: " + booking.getPassengerId());
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        // TODO: integrate JavaMail or SendGrid for production
        System.out.println("[EMAIL] Cancellation notice sent for PNR: " + booking.getPnr()
            + " | Refund initiated for Passenger: " + booking.getPassengerId());
    }
}
````

## File: src/main/java/com/airline/system/patterns/FlightFactory.java
````java
package com.airline.system.patterns;

import com.airline.system.enums.FlightStatus;
import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;

import java.time.Duration;
import java.util.Map;

/**
 * Factory Pattern — Creational
 * Owner: Alekhya (CS053)
 *
 * Problem solved: Without Factory, every endpoint / service that creates a
 * Flight would duplicate field-mapping and type-selection logic.  FlightFactory
 * centralises construction and delegates type-specific rules to FlightType
 * implementations, honouring both the Factory Pattern and OCP.
 *
 * Adding a new flight type (e.g. "CHARTER") only requires:
 *   1. A new FlightType implementation.
 *   2. One line in the FLIGHT_TYPES map below.
 *   No other class needs to change.
 */
public class FlightFactory {

    /**
     * Registry mapping type strings to their FlightType strategy.
     * Using a Map instead of if-else eliminates branching and makes the
     * factory trivially extensible (OCP).
     */
    private static final Map<String, FlightType> FLIGHT_TYPES = Map.of(
            "DOMESTIC",      new DomesticFlight(),
            "INTERNATIONAL", new InternationalFlight()
    );

    /**
     * Build and return a fully initialised Flight entity from a FlightRequest.
     *
     * @param request incoming DTO carrying all user-supplied fields
     * @return a ready-to-persist Flight entity with type rules applied
     * @throws IllegalArgumentException if the requested type is unknown
     */
    public static Flight createFlight(FlightRequest request) {

        // ── 1. Resolve flight type strategy ──────────────────────────────────
        String typeKey = (request.getType() == null) ? "" : request.getType().toUpperCase();
        FlightType flightType = FLIGHT_TYPES.get(typeKey);
        if (flightType == null) {
            throw new IllegalArgumentException(
                    "Unknown flight type: '" + request.getType() +
                    "'. Accepted values: " + FLIGHT_TYPES.keySet());
        }

        // ── 2. Map DTO → entity ───────────────────────────────────────────────
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAirlineName(request.getAirlineName());
        flight.setSource(request.getSource());
        flight.setDestination(request.getDestination());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setAvailableSeats(request.getTotalSeats());   // all seats open on creation
        flight.setBasePrice(request.getBasePrice());
        flight.setStatus(FlightStatus.SCHEDULED);

        // ── 3. Duration (optional enhancement) ───────────────────────────────
        if (request.getDepartureTime() != null && request.getArrivalTime() != null) {
            int minutes = (int) Duration.between(
                    request.getDepartureTime(), request.getArrivalTime()).toMinutes();
            flight.setDuration(minutes);
        }

        // ── 4. Apply type-specific rules via polymorphism ─────────────────────
        flightType.applyRules(flight);

        return flight;
    }
}
````

## File: src/main/java/com/airline/system/patterns/FlightType.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * Open/Closed Principle — Strategy interface for flight type rules.
 *
 * The system is OPEN for extension (add new flight types by implementing this
 * interface) but CLOSED for modification (FlightFactory never needs an if-else
 * chain; it simply calls applyRules polymorphically).
 *
 * Owner: Alekhya (CS053)
 */
public interface FlightType {

    /**
     * Apply type-specific business rules to the given flight.
     * Implementations may alter price, add surcharges, validate constraints, etc.
     *
     * @param flight the freshly constructed Flight entity to mutate
     */
    void applyRules(Flight flight);
}
````

## File: src/main/java/com/airline/system/patterns/InternationalFlight.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Flight;

/**
 * OCP Implementation — International flight rules.
 *
 * International routes incur additional operational costs (customs, longer
 * ground handling, fuel surcharges). We model this as a 20 % mark-up on the
 * base price that was supplied by the operator.
 *
 * Owner: Alekhya (CS053)
 */
public class InternationalFlight implements FlightType {

    private static final double INTERNATIONAL_SURCHARGE = 0.20;

    @Override
    public void applyRules(Flight flight) {
        double adjustedPrice = flight.getBasePrice() * (1 + INTERNATIONAL_SURCHARGE);
        flight.setBasePrice(Math.round(adjustedPrice * 100.0) / 100.0); // round to 2 dp
    }
}
````

## File: src/main/java/com/airline/system/patterns/ReportFacade.java
````java
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
````

## File: src/main/java/com/airline/system/patterns/SeatAvailabilityObserver.java
````java
package com.airline.system.patterns;

import com.airline.system.model.Booking;
import com.airline.system.service.SeatService;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern — Concrete Observer 2
 * Owner: Alekhya (CS053)
 *
 * Reacts to booking events by updating seat availability on the flight.
 * Decoupled from BookingService — no direct call needed.
 */
@Component
public class SeatAvailabilityObserver implements BookingObserver {

    private final SeatService seatService;

    public SeatAvailabilityObserver(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public void onBookingConfirmed(Booking booking) {
        seatService.decrementAvailableSeats(
            booking.getFlightId(), booking.getNumberOfPassengers());
        System.out.println("[SEATS] Decremented " + booking.getNumberOfPassengers()
            + " seat(s) for flight: " + booking.getFlightId());
    }

    @Override
    public void onBookingCancelled(Booking booking) {
        seatService.incrementAvailableSeats(
            booking.getFlightId(), booking.getNumberOfPassengers());
        System.out.println("[SEATS] Restored " + booking.getNumberOfPassengers()
            + " seat(s) for flight: " + booking.getFlightId());
    }
}
````

## File: src/main/java/com/airline/system/repository/FlightRepository.java
````java
package com.airline.system.repository;

import com.airline.system.enums.FlightStatus;
import com.airline.system.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    List<Flight> findBySourceAndDestinationAndDepartureTimeBetween(
        String source, String destination,
        LocalDateTime from, LocalDateTime to);
    List<Flight> findByStatus(FlightStatus status);
}
````

## File: src/main/java/com/airline/system/repository/PaymentRepository.java
````java
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
````

## File: src/main/java/com/airline/system/repository/ReportRepository.java
````java
package com.airline.system.repository;

import com.airline.system.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/** Owner: 4th Member (PES1UG23CS___) */
@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByReportType(String reportType);
    List<Report> findByFromDateGreaterThanEqualAndToDateLessThanEqual(
        LocalDate from, LocalDate to);
}
````

## File: src/main/java/com/airline/system/service/PaymentService.java
````java
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
````

## File: src/main/java/com/airline/system/service/ReportService.java
````java
package com.airline.system.service;

import com.airline.system.model.Report;
import com.airline.system.repository.ReportRepository;
import org.springframework.stereotype.Service;

/**
 * Owner: 4th Member (PES1UG23CS___)
 * Full implementation to be added by 4th member.
 */
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
}
````

## File: src/main/java/com/airline/system/SystemApplication.java
````java
package com.airline.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
````

## File: docker-compose.yml
````yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: airline_db
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: airline123
      MYSQL_DATABASE: airline_db
    ports:
      - "3306:3306"
    volumes:
      - airline_data:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  airline_data:
````

## File: package.json
````json
{
  "dependencies": {
    "repomix": "^1.13.1"
  }
}
````

## File: pom.xml
````xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>

    <groupId>com.airline</groupId>
    <artifactId>system</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>airline-system</name>
    <description>Airline Management System - OOAD Mini Project UE23CS352B</description>

    <properties>
        <java.version>17</java.version>
    </properties>

    <dependencies>
        <!-- Spring Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Data JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- MySQL Driver -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Spring Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.30</version>
            <scope>provided</scope>
        </dependency>

        <!-- H2 for testing -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Spring Boot Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
    
            <!-- ✅ ADD THIS (VERY IMPORTANT) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>1.18.30</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
    
            <!-- Existing Spring Boot plugin -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
    
        </plugins>
    </build>
</project>
````

## File: src/main/java/com/airline/system/controller/BookingController.java
````java
package com.airline.system.controller;

import com.airline.system.model.Booking;
import com.airline.system.model.BookingDetailDTO;
import com.airline.system.model.BookingRequest;
import com.airline.system.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Owner: Aditi (CS029) */
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest req) {
        Booking b = bookingService.createBooking(
            req.getFlightId(),
            req.getPassengerId(),
            req.getSeatType(),
            req.getPassengerCount(),
            req.getSeatIds());      // pass selected seat IDs
        return ResponseEntity.ok(b);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<Booking> getHistory(@PathVariable String passengerId) {
        return bookingService.getBookingHistory(passengerId);
    }

    /** New: enriched booking details with flight + seat info */
    @GetMapping("/passenger/{passengerId}/details")
    public List<BookingDetailDTO> getDetails(@PathVariable String passengerId) {
        return bookingService.getBookingDetails(passengerId);
    }

    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<String> cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled. Refund initiated.");
    }
}
````

## File: src/main/java/com/airline/system/controller/FlightController.java
````java
package com.airline.system.controller;

import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;
import com.airline.system.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/** Owner: Alekhya (CS053) */
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.searchFlights(null, null, null);
    }

    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String src,
                                      @RequestParam String dest,
                                      @RequestParam String date) {
        return flightService.searchFlights(src, dest, LocalDateTime.parse(date));
    }

    /**
     * Controller only accepts the DTO and delegates to the service.
     * No Flight object is created here — Factory Pattern responsibility
     * is fully in FlightFactory via FlightService.
     */
    @PostMapping
    public ResponseEntity<Flight> addFlight(@RequestBody FlightRequest request) {
        return ResponseEntity.ok(flightService.addFlight(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable String id,
                                               @RequestBody Flight details) {
        return ResponseEntity.ok(flightService.updateFlight(id, details));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok("Flight deleted");
    }
}
````

## File: src/main/java/com/airline/system/controller/SeatController.java
````java
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
    public SeatController(SeatService seatService) { this.seatService = seatService; }

    /** All seats for a flight (available + taken) — used by seat map */
    @GetMapping("/{flightId}/all")
    public List<Seat> getAllSeats(@PathVariable String flightId) {
        return seatService.getAllSeats(flightId);
    }

    /** Available seats only */
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
````

## File: src/main/java/com/airline/system/model/Booking.java
````java
package com.airline.system.model;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/** Owner: Aditi (CS029) */
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
}
````

## File: src/main/java/com/airline/system/model/BookingRequest.java
````java
package com.airline.system.model;

import com.airline.system.enums.SeatType;
import lombok.Data;
import java.util.List;

/**
 * DTO used by BookingController to receive booking request body.
 * Owner: Aditi (CS029)
 */
@Data
public class BookingRequest {
    private String flightId;
    private String passengerId;
    private SeatType seatType;
    private int passengerCount;
    // New: specific seat IDs the passenger selected
    private List<String> seatIds;
}
````

## File: src/main/java/com/airline/system/model/Flight.java
````java
package com.airline.system.model;

import com.airline.system.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Entity
@Data
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String flightId;

    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int duration;
    private int totalSeats;
    private int availableSeats;
    private double basePrice;

    @Enumerated(EnumType.STRING)
    private FlightStatus status = FlightStatus.SCHEDULED;
    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}
````

## File: src/main/java/com/airline/system/model/FlightRequest.java
````java
package com.airline.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

/**
 * DTO for creating a new Flight.
 * Decouples the HTTP request payload from the JPA entity.
 * Owner: Alekhya (CS053)
 */
public class FlightRequest {

    private String flightNumber;
    private String airlineName;
    private String source;
    private String destination;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime arrivalTime;
    private int totalSeats;
    private double basePrice;

    /**
     * Flight type discriminator: "DOMESTIC" or "INTERNATIONAL".
     * Used by FlightFactory to select the correct FlightType strategy (OCP).
     */
    private String type;

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getFlightNumber()             { return flightNumber; }
    public void   setFlightNumber(String v)     { this.flightNumber = v; }

    public String getAirlineName()              { return airlineName; }
    public void   setAirlineName(String v)      { this.airlineName = v; }

    public String getSource()                   { return source; }
    public void   setSource(String v)           { this.source = v; }

    public String getDestination()              { return destination; }
    public void   setDestination(String v)      { this.destination = v; }

    public LocalDateTime getDepartureTime()     { return departureTime; }
    public void   setDepartureTime(LocalDateTime v) { this.departureTime = v; }

    public LocalDateTime getArrivalTime()       { return arrivalTime; }
    public void   setArrivalTime(LocalDateTime v)   { this.arrivalTime = v; }

    public int    getTotalSeats()               { return totalSeats; }
    public void   setTotalSeats(int v)          { this.totalSeats = v; }

    public double getBasePrice()                { return basePrice; }
    public void   setBasePrice(double v)        { this.basePrice = v; }

    public String getType()                     { return type; }
    public void   setType(String v)             { this.type = v; }
}
````

## File: src/main/java/com/airline/system/patterns/BookingBuilder.java
````java
package com.airline.system.patterns;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.Booking;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Builder Pattern — Creational
 * Owner: Aditi (CS029)
 *
 * Problem solved: Booking has many fields. Without Builder, a 10-parameter
 * constructor is needed. Builder lets each step of the booking flow add one
 * field at a time before calling build().
 */
public class BookingBuilder 
{
    private String flightId;
    private String passengerId;
    private SeatType seatType;
    private int numPassengers;
    private LocalDateTime bookingDate;
    public BookingBuilder withFlight(String flightId) {
        this.flightId = flightId; return this;
    }
    public BookingBuilder withPassenger(String passengerId) {
        this.passengerId = passengerId; return this;
    }
    public BookingBuilder withSeatType(SeatType seatType) {
        this.seatType = seatType; return this;
    }
    public BookingBuilder withPassengerCount(int count) {
        this.numPassengers = count; return this;
    }
    public Booking build() {
        Booking b = new Booking();
        b.setFlightId(flightId);
        b.setPassengerId(passengerId);
        b.setSeatType(seatType);
        b.setNumberOfPassengers(numPassengers);
        b.setBookingDate(LocalDateTime.now());
        b.setStatus(BookingStatus.PENDING);
        b.setPnr(generatePNR());
        return b;
    }
    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
````

## File: src/main/java/com/airline/system/patterns/UserFactory.java
````java
package com.airline.system.patterns;

import com.airline.system.enums.UserRole;
import com.airline.system.model.Administrator;
import com.airline.system.model.Passenger;
import com.airline.system.model.Staff;
import com.airline.system.model.User;

/**
 * Factory Pattern — Creational
 * Owner: Alekhya (CS053)
 * Updated: accepts phone number for registration
 */
public class UserFactory {

    public static User createUser(UserRole role, String username,
                                   String email, String password, String phone) {
        return switch (role) {
            case PASSENGER -> {
                Passenger p = new Passenger();
                p.setUsername(username); p.setEmail(email);
                p.setPassword(password); p.setPhone(phone);
                p.setRole(UserRole.PASSENGER); p.setActive(true);
                yield p;
            }
            case STAFF -> {
                Staff s = new Staff();
                s.setUsername(username); s.setEmail(email);
                s.setPassword(password); s.setPhone(phone);
                s.setRole(UserRole.STAFF); s.setActive(true);
                yield s;
            }
            case ADMIN -> {
                Administrator a = new Administrator();
                a.setUsername(username); a.setEmail(email);
                a.setPassword(password); a.setPhone(phone);
                a.setRole(UserRole.ADMIN); a.setActive(true);
                yield a;
            }
        };
    }
}
````

## File: src/main/java/com/airline/system/repository/BookingRepository.java
````java
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
    // Fix: needed by FlightService to cancel bookings when a flight is deleted
    List<Booking> findByFlightId(String flightId);
}
````

## File: src/main/java/com/airline/system/repository/SeatRepository.java
````java
package com.airline.system.repository;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/** Owner: Aditi (CS029) */
@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    List<Seat> findByFlightFlightId(String flightId);   // all seats (taken + available)
    List<Seat> findByFlightFlightIdAndIsAvailableTrue(String flightId);
    List<Seat> findByFlightFlightIdAndSeatType(String flightId, SeatType seatType);
    List<Seat> findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(String flightId, SeatType seatType);
}
````

## File: src/main/java/com/airline/system/service/UserService.java
````java
package com.airline.system.service;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.patterns.UserFactory;
import com.airline.system.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/** Owner: Pranav (CS002) */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Duplicate checks:
     * 1. Same username + same role → "already registered as PASSENGER/STAFF/ADMIN"
     * 2. Same email (any role)     → "email already in use"
     */
    public User registerUser(UserRole role, String username, String email,
                              String password, String phone) {
        if (userRepository.existsByUsernameAndRole(username, role)) {
            throw new RuntimeException(
                "Username '" + username + "' is already registered as " + role +
                ". Please log in instead.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException(
                "Email '" + email + "' is already in use by another account.");
        }
        User newUser = UserFactory.createUser(role, username, email, password, phone);
        return userRepository.save(newUser);
    }

    public User findByUsernameAndRole(String username, UserRole role) {
        return userRepository.findByUsernameAndRole(username, role)
            .orElseThrow(() -> new RuntimeException(
                "No " + role + " account found for username: " + username));
    }

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public void setUserActive(String userId, boolean active) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setActive(active);
            userRepository.save(user);
        });
    }
}
````

## File: .gitignore
````
# Node modules
node_modules/

# Build output
target/
*.class
*.jar
*.war

# IDE files
.idea/
*.iml
.vscode/
*.swp
.DS_Store

# !! NEVER COMMIT REAL CREDENTIALS !!
src/main/resources/application-local.properties

# Docker local volumes
docker/data/
````

## File: src/main/java/com/airline/system/controller/AuthController.java
````java
package com.airline.system.controller;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import com.airline.system.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        try {
            User user = userService.registerUser(
                req.getRole(), req.getUsername(), req.getEmail(),
                req.getPassword(), req.getPhone());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // Return 409 Conflict with the error message so frontend can show alert
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            User user = userService.findByUsernameAndRole(req.getUsername(), req.getRole());
            return ResponseEntity.ok(new LoginResponse(
                user.getUserId(), user.getUsername(),
                user.getRole().toString(), user.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<String> setStatus(@PathVariable String id,
                                             @RequestParam boolean active) {
        userService.setUserActive(id, active);
        return ResponseEntity.ok("Status updated");
    }

    @Data static class RegisterRequest {
        private String username, email, password, phone;
        private UserRole role;
    }
    @Data static class LoginRequest {
        private String username, password;
        private UserRole role;
    }
    @Data static class LoginResponse {
        private String userId, username, role, email;
        public LoginResponse(String userId, String username, String role, String email) {
            this.userId = userId; this.username = username;
            this.role = role; this.email = email;
        }
    }
}
````

## File: src/main/java/com/airline/system/model/Seat.java
````java
package com.airline.system.model;

import com.airline.system.enums.SeatType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String seatId;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private boolean isAvailable = true;

    @Column(name = "seat_row")      // was row_number, now seat_row
    private int row;

    @Column(name = "seat_column")
    private char seatColumn;

    private double price;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
}
````

## File: src/main/java/com/airline/system/repository/UserRepository.java
````java
package com.airline.system.repository;

import com.airline.system.enums.UserRole;
import com.airline.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/** Owner: Pranav (CS002) */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndRole(String username, UserRole role);
    // For duplicate-email check across ALL roles
    Optional<User> findByEmail(String email);
    // For duplicate username+role check at registration
    boolean existsByUsernameAndRole(String username, UserRole role);
    List<User> findByRole(UserRole role);
}
````

## File: src/main/java/com/airline/system/service/BookingService.java
````java
package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.*;
import com.airline.system.patterns.BookingBuilder;
import com.airline.system.patterns.BookingObserver;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Owner: Aditi (CS029)
 * SRP: only booking lifecycle — create, confirm, cancel, history.
 * Does NOT handle payments or emails (separate services/observers).
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final SeatService seatService;
    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final List<BookingObserver> observers;

    public BookingService(BookingRepository bookingRepository,
                          SeatService seatService,
                          SeatRepository seatRepository,
                          FlightRepository flightRepository,
                          List<BookingObserver> observers) {
        this.bookingRepository = bookingRepository;
        this.seatService = seatService;
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
        this.observers = observers;
    }

    /**
     * Creates a booking and marks the chosen seat IDs as unavailable.
     * seatIds: specific seat IDs the passenger selected (from /api/seats/{flightId}).
     *          If empty/null, falls back to auto-assigning first available seats.
     */
    public Booking createBooking(String flightId, String passengerId,
                                  SeatType seatType, int count,
                                  List<String> seatIds) {
        if (!seatService.hasAvailableSeats(flightId, count))
            throw new RuntimeException("Not enough seats available");

        Booking booking = new BookingBuilder()
            .withFlight(flightId)
            .withPassenger(passengerId)
            .withSeatType(seatType)
            .withPassengerCount(count)
            .build();

        Booking saved = bookingRepository.save(booking);

        // Mark chosen seats as unavailable
        List<String> assignedNums = new ArrayList<>();
        if (seatIds != null && !seatIds.isEmpty()) {
            for (String seatId : seatIds) {
                seatRepository.findById(seatId).ifPresent(seat -> {
                    seat.setAvailable(false);
                    seatRepository.save(seat);
                    assignedNums.add(seat.getSeatNumber());
                });
            }
        } else {
            // Auto-assign: pick first N available seats of the right type
            List<Seat> autoSeats = seatRepository
                .findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType)
                .stream().limit(count).toList();
            for (Seat seat : autoSeats) {
                seat.setAvailable(false);
                seatRepository.save(seat);
                assignedNums.add(seat.getSeatNumber());
            }
        }

        // Decrement flight-level counter
        seatService.decrementAvailableSeats(flightId, count);

        observers.forEach(o -> o.onBookingConfirmed(saved));
        return saved;
    }

    public void cancelBooking(String bookingId) {
        Booking b = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));
        b.setStatus(BookingStatus.CANCELLED);

        // Re-open the seats so others can book them
        // (seats linked to this booking via ticket seatNumbers — use flightId + seatNumber lookup)
        // Simple approach: increment flight counter back
        seatService.incrementAvailableSeats(b.getFlightId(), b.getNumberOfPassengers());

        Booking saved = bookingRepository.save(b);
        observers.forEach(o -> o.onBookingCancelled(saved));
    }

    public List<Booking> getBookingHistory(String passengerId) {
        return bookingRepository.findByPassengerId(passengerId);
    }

    /**
     * Enriched booking list for the passenger "My Bookings" view.
     * Joins Booking + Flight + Seat data into a single DTO per booking.
     */
    public List<BookingDetailDTO> getBookingDetails(String passengerId) {
        List<Booking> bookings = bookingRepository.findByPassengerId(passengerId);
        return bookings.stream().map(b -> {
            BookingDetailDTO dto = new BookingDetailDTO();
            dto.setBookingId(b.getBookingId());
            dto.setPnr(b.getPnr());
            dto.setStatus(b.getStatus());
            dto.setSeatType(b.getSeatType());
            dto.setNumberOfPassengers(b.getNumberOfPassengers());
            dto.setBookingDate(b.getBookingDate());

            // Enrich with flight info
            flightRepository.findById(b.getFlightId()).ifPresent(f -> {
                dto.setFlightNumber(f.getFlightNumber());
                dto.setAirlineName(f.getAirlineName());
                dto.setSource(f.getSource());
                dto.setDestination(f.getDestination());
                dto.setDepartureTime(f.getDepartureTime());
                dto.setArrivalTime(f.getArrivalTime());
            });

            // Collect seat numbers that are booked (unavailable) for this flight
            // and match the passenger count for this booking
            List<Seat> bookedSeats = seatRepository
                .findByFlightFlightIdAndSeatType(b.getFlightId(), b.getSeatType())
                .stream()
                .filter(s -> !s.isAvailable())
                .limit(b.getNumberOfPassengers())
                .toList();

            dto.setSeatNumbers(bookedSeats.stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }

    public List<Booking> getBookingsBetween(LocalDate from, LocalDate to) {
        return bookingRepository.findByBookingDateBetween(
            from.atStartOfDay(), to.atTime(23, 59));
    }

    public List<Booking> getCancelledBetween(LocalDate from, LocalDate to) {
        return bookingRepository.findByBookingDateBetween(
            from.atStartOfDay(), to.atTime(23, 59))
            .stream()
            .filter(b -> b.getStatus() == BookingStatus.CANCELLED)
            .toList();
    }
}
````

## File: src/main/java/com/airline/system/service/SeatService.java
````java
package com.airline.system.service;

import com.airline.system.enums.SeatType;
import com.airline.system.model.Seat;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;

    public SeatService(SeatRepository seatRepository, FlightRepository flightRepository) {
        this.seatRepository = seatRepository;
        this.flightRepository = flightRepository;
    }

    public List<Seat> getAllSeats(String flightId) {
        return seatRepository.findByFlightFlightId(flightId);
    }

    public List<Seat> getAvailableSeats(String flightId) {
        return seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId);
    }

    public List<Seat> getSeatsByType(String flightId, SeatType seatType) {
        return seatRepository.findByFlightFlightIdAndSeatTypeAndIsAvailableTrue(flightId, seatType);
    }

    public boolean hasAvailableSeats(String flightId, int requiredCount) {
        return flightRepository.findById(flightId)
            .map(f -> f.getAvailableSeats() >= requiredCount)
            .orElse(false);
    }

    public void decrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(f -> {
            f.setAvailableSeats(Math.max(0, f.getAvailableSeats() - count));
            flightRepository.save(f);
        });
    }

    public void incrementAvailableSeats(String flightId, int count) {
        flightRepository.findById(flightId).ifPresent(f -> {
            f.setAvailableSeats(f.getAvailableSeats() + count);
            flightRepository.save(f);
        });
    }
}
````

## File: README.md
````markdown
# ✈️ Airline Management System
### UE23CS352B — Object Oriented Analysis & Design | PES University | 2025–26

---

## What is this project?

This is a full-stack **Airline Management System** built as part of our OOAD mini-project. The idea came from wanting to model a real-world system that naturally maps to the concepts we studied — multiple user roles, complex object relationships, and interactions that lend themselves well to design patterns and SOLID principles.

The system handles three types of users: **Passengers** who search and book flights, **Staff** who manage flight operations, and **Admins** who oversee users and generate reports. Each role has its own set of use cases, and the entire backend is built with Spring Boot following the MVC architecture.

The reason we picked this domain is honestly because it's complex enough to justify proper OOP design — you've got inheritance (different user types), behavioral patterns (booking triggers multiple reactions), structural patterns (reports need data from multiple services), and creational patterns (object construction that varies by type). It wasn't just a project to submit — it ended up being a decent exercise in actually applying what we learned in class.

---

## Team

| SRN | Name | Major Use Case | Design Pattern | SOLID Principle |
|---|---|---|---|---|
| PES1UG23CS029 | Aditi Shankar | Booking & Payment | Builder | SRP |
| PES1UG23CS053 | Alekhya Agaram | Flight Management + Reports & Admin | Factory + Observer + Facade | OCP + DIP |
| PES1UG23CS002 | A K Pranav | User Auth & Roles | Singleton (DB) | LSP |

---

## OOAD Concepts Applied

This section maps the actual guidelines to what we implemented. We tried to make sure each concept was meaningfully used rather than just bolted on.

### MVC Architecture

The entire backend follows Spring Boot's MVC structure strictly:

- **Controller layer** — handles HTTP requests, does zero business logic, just delegates to services
- **Service layer** — all business rules live here (e.g. a booking can't be made if seats aren't available, a flight can't be deleted if it has active bookings)
- **Repository layer** — Spring Data JPA, no raw SQL, auto-generated queries
- **Model layer** — JPA entities mapped to MySQL tables

No controller ever talks to a repository directly. No repository has business logic. This separation made it easy to test and debug individual layers.

### Design Patterns

We implemented 4 design patterns spanning all three GoF categories (which is the requirement for a 4-concept team — we covered Creational, Structural, and Behavioral):

**Builder Pattern — Creational (Aditi)**
`patterns/BookingBuilder.java`

A `Booking` object has a lot of fields — flight ID, passenger ID, seat type, passenger count, PNR, booking date, status. Using a single constructor would mean passing 8+ parameters in order, which is unreadable and error-prone. Builder lets the booking be assembled step by step as the passenger goes through the booking flow, and `build()` creates the final valid object with sensible defaults (status = PENDING, date = now, auto-generated PNR).

**Factory Pattern — Creational (Alekhya)**
`patterns/FlightFactory.java` + `patterns/UserFactory.java`

Two factory implementations. `UserFactory` centralises the creation of `Passenger`, `Staff`, or `Administrator` objects based on role — without it, every registration endpoint would have if-else chains. `FlightFactory` uses a strategy map to handle DOMESTIC vs INTERNATIONAL flight rules (international gets a 20% surcharge applied automatically), meaning new flight types can be added with zero changes to existing code.

**Facade Pattern — Structural (Alekhya)**
`patterns/ReportFacade.java`

Generating an admin report means querying `BookingService`, `FlightService`, and `PaymentService`, then aggregating the results. Without Facade, the controller would be tightly coupled to all three services. `ReportFacade` hides all of that behind two clean methods: `generateRevenueReport()` and `generateCancellationReport()`. The controller calls one method and gets back a complete report.

**Observer Pattern — Behavioral (Pranav + Alekhya)**
`patterns/BookingObserver.java` and implementations

When a booking is confirmed, two things need to happen independently: send an email notification, and decrement the available seat count on the flight. Without Observer, `BookingService` would have to call both services directly — tight coupling. Instead, `BookingService` notifies all registered observers, and `EmailNotificationObserver` and `SeatAvailabilityObserver` react independently. Adding a new reaction (e.g. generating a ticket) just means adding a new observer — `BookingService` never changes.

### SOLID Principles

**SRP — Single Responsibility (Aditi)**
`BookingService` only handles booking lifecycle. `PaymentService` only handles payment processing. They're kept strictly separate even though a booking triggers a payment — because the reason to change each class is different.

**OCP — Open/Closed (Alekhya)**
The `FlightType` interface + `DomesticFlight` / `InternationalFlight` implementations mean flight types are open for extension but closed for modification. Adding a `CharterFlight` type means writing one new class and adding one line to the registry map in `FlightFactory` — no existing code changes.

**LSP — Liskov Substitution (Pranav)**
`Passenger`, `Staff`, and `Administrator` all extend the abstract `User` class. Any method that accepts a `User` works correctly with any subtype. Spring Security's `UserDetailsService` operates on `User` objects without knowing the concrete type. No subclass throws `UnsupportedOperationException` for inherited methods.

**DIP — Dependency Inversion (Alekhya)**
`ReportFacade` depends on `BookingService`, `FlightService`, and `PaymentService` as abstractions. Spring injects the concrete implementations at runtime via constructor injection. Controllers declare service dependencies as interfaces, not implementations — making unit testing with mocks straightforward.

---

## Tech Stack

- **Backend:** Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA
- **Database:** MySQL (Railway cloud or local Docker)
- **Build:** Maven, Lombok
- **Frontend:** Vanilla HTML/CSS/JS served as a static file from Spring Boot

---

## Setup & Running the Project

You have two options for the database — Railway (shared cloud, recommended) or local Docker.

---

### Option A — Railway Cloud DB (Recommended)

This is the shared database. Everyone on the team connects to the same instance — no local MySQL needed.

**Step 1 — Clone the repo**
```bash
git clone https://github.com/AditiShankar3/Airline-management-system.git
cd Airline-management-system
```

**Step 2 — Get credentials**
Get `application-local.properties` from Aditi (sent via WhatsApp). Place it here:
```
src/main/resources/application-local.properties
```
It looks like this — fill in the actual Railway values:
```properties
DB_URL=jdbc:mysql://YOUR_RAILWAY_HOST:PORT/railway
DB_USERNAME=root
DB_PASSWORD=YOUR_RAILWAY_PASSWORD
```
> ⚠️ This file is in `.gitignore` — never commit it.

**Step 3 — Run**
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.config.additional-location=classpath:application-local.properties"
```

**Step 4 — Open the app**
```
http://localhost:8081/index.html
```

---

### Option B — Local MySQL via Docker (Offline fallback)

Use this if you're offline or don't have the Railway credentials yet.

**Step 1 — Start MySQL container**
```bash
docker-compose up -d
```
This starts a MySQL instance on port 3306 with:
- Database: `airline_db`
- Username: `root`
- Password: `airline123`

**Step 2 — Run the app**
```bash
mvn spring-boot:run
```
Spring Boot will use `localhost:3306` by default (no local properties file needed).

**Step 3 — Open the app**
```
http://localhost:8081/index.html
```

**Stop Docker when done:**
```bash
docker-compose down
```

---

### Option C — Your own MySQL

If you have MySQL installed locally, just update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airline_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
Then create the database:
```sql
CREATE DATABASE airline_db;
```
Spring Boot will create all tables automatically on first run (`ddl-auto=update`).

---

## Setting up Railway from scratch (for reference)

If you need to create a new Railway MySQL instance:

1. Go to [railway.app](https://railway.app) → sign in with GitHub
2. New Project → Deploy a Template → select MySQL → Deploy
3. Click the MySQL service → Variables tab
4. Copy `MYSQL_HOST`, `MYSQLPORT`, `MYSQL_DATABASE`, `MYSQL_USER`, `MYSQL_PASSWORD`
5. Build your `application-local.properties` from those values:
   ```
   DB_URL=jdbc:mysql://MYSQL_HOST:MYSQLPORT/MYSQL_DATABASE
   DB_USERNAME=MYSQL_USER
   DB_PASSWORD=MYSQL_PASSWORD
   ```
6. Run the app — tables are auto-created on first startup

**Note:** Railway free tier uses a non-standard port (not 3306). Make sure you use the port from the Variables tab, not 3306.

---

## API Endpoints Reference

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user (role in body: PASSENGER / STAFF / ADMIN) |
| POST | `/api/auth/login` | Login — returns userId, username, role |
| GET | `/api/auth/users` | List all users (admin) |
| PUT | `/api/auth/users/{id}/status?active=true` | Activate or deactivate a user |

### Flights
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/flights` | Get all flights |
| GET | `/api/flights/search?src=&dest=&date=` | Search by route and date |
| POST | `/api/flights` | Add new flight (requires `type`: DOMESTIC or INTERNATIONAL) |
| PUT | `/api/flights/{id}` | Update flight details |
| DELETE | `/api/flights/{id}` | Delete a flight |

### Bookings
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/bookings` | Create a booking |
| GET | `/api/bookings/passenger/{id}` | Booking history for a passenger |
| GET | `/api/bookings/passenger/{id}/details` | Enriched booking info with flight details |
| PUT | `/api/bookings/{id}/cancel` | Cancel a booking |

### Payments
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/payments` | Process payment for a booking |
| PUT | `/api/payments/refund/{bookingId}` | Refund a payment |

### Seats
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/seats/{flightId}` | Available seats for a flight |
| GET | `/api/seats/{flightId}/all` | All seats (available + taken) |
| GET | `/api/seats/{flightId}/type?seatType=ECONOMY` | Filter by seat type |

### Reports (Admin only)
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/reports/revenue?from=YYYY-MM-DD&to=YYYY-MM-DD` | Revenue report |
| GET | `/api/reports/cancellations?from=YYYY-MM-DD&to=YYYY-MM-DD` | Cancellation report |

---

## Sample Postman requests

**Register a passenger:**
```json
POST /api/auth/register
{
  "username": "alice",
  "email": "alice@demo.com",
  "password": "pass123",
  "role": "PASSENGER"
}
```

**Add a domestic flight (Staff):**
```json
POST /api/flights
{
  "flightNumber": "AI101",
  "airlineName": "Air India",
  "source": "Bangalore",
  "destination": "Mumbai",
  "departureTime": "2026-05-01T10:00:00",
  "arrivalTime": "2026-05-01T12:00:00",
  "totalSeats": 180,
  "basePrice": 3500.00,
  "type": "DOMESTIC"
}
```

**Book a flight:**
```json
POST /api/bookings
{
  "flightId": "<flight-id>",
  "passengerId": "<user-id>",
  "seatType": "ECONOMY",
  "passengerCount": 1
}
```

**Process payment:**
```json
POST /api/payments
{
  "bookingId": "<booking-id>",
  "amount": 3500.00,
  "paymentMethod": "UPI"
}
```

---

## Git workflow

```bash
# Always pull before starting
git pull origin main

# After your changes
git add .
git commit -m "feat(module): description - Name SRN"
git push origin main
```

---

## GitHub

[github.com/AditiShankar3/Airline-management-system](https://github.com/AditiShankar3/Airline-management-system)
````

## File: src/main/java/com/airline/system/service/FlightService.java
````java
package com.airline.system.service;

import com.airline.system.enums.BookingStatus;
import com.airline.system.enums.SeatType;
import com.airline.system.model.Booking;
import com.airline.system.model.Flight;
import com.airline.system.model.FlightRequest;
import com.airline.system.model.Seat;
import com.airline.system.patterns.FlightFactory;
import com.airline.system.repository.BookingRepository;
import com.airline.system.repository.FlightRepository;
import com.airline.system.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Owner: Alekhya (CS053) */
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public FlightService(FlightRepository flightRepository,
                         BookingRepository bookingRepository,
                         SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Adds a flight AND auto-seeds its seat map so the seat picker works immediately.
     * Layout: rows 1–totalSeats/6, columns A–F.
     *   Rows 1–2         → FIRST_CLASS   (cols A–D, 4 per row)
     *   Rows 3–(n/3)     → BUSINESS      (cols A–F, 6 per row)
     *   Remaining rows   → ECONOMY       (cols A–F, 6 per row)
     */
    public Flight addFlight(FlightRequest request) {
        Flight flight = FlightFactory.createFlight(request);
        Flight saved = flightRepository.save(flight);
        seedSeats(saved);
        return saved;
    }

    private void seedSeats(Flight flight) {
        int total = flight.getTotalSeats() > 0 ? flight.getTotalSeats() : 180;
        List<Seat> seats = new ArrayList<>();

        // First class: rows 1–2, cols A-D  (8 seats)
        for (int row = 1; row <= 2; row++) {
            for (char col : new char[]{'A','B','C','D'}) {
                seats.add(makeSeat(flight, row, col, SeatType.FIRST_CLASS));
            }
        }
        // Business: rows 3–(firstClassRows + businessRows), cols A-F
        int businessRows = Math.max(2, total / 20);
        for (int row = 3; row < 3 + businessRows; row++) {
            for (char col : new char[]{'A','B','C','D','E','F'}) {
                seats.add(makeSeat(flight, row, col, SeatType.BUSINESS));
            }
        }
        // Economy: fill remaining
        int ecoStart = 3 + businessRows;
        int ecoRows = Math.max(5, (total - seats.size()) / 6);
        for (int row = ecoStart; row < ecoStart + ecoRows; row++) {
            for (char col : new char[]{'A','B','C','D','E','F'}) {
                seats.add(makeSeat(flight, row, col, SeatType.ECONOMY));
            }
        }

        seatRepository.saveAll(seats);
    }

    private Seat makeSeat(Flight flight, int row, char col, SeatType type) {
        Seat s = new Seat();
        s.setSeatNumber(row + String.valueOf(col));
        s.setRow(row);
        s.setSeatColumn(col);
        s.setSeatType(type);
        s.setAvailable(true);
        s.setFlight(flight);
        s.setPrice(switch (type) {
            case FIRST_CLASS -> flight.getBasePrice() * 3;
            case BUSINESS    -> flight.getBasePrice() * 1.8;
            case ECONOMY     -> flight.getBasePrice();
        });
        return s;
    }

    public Flight updateFlight(String flightId, Flight details) {
        Flight f = flightRepository.findById(flightId)
            .orElseThrow(() -> new RuntimeException("Flight not found"));
        f.setFlightNumber(details.getFlightNumber());
        f.setAirlineName(details.getAirlineName());
        f.setDepartureTime(details.getDepartureTime());
        f.setBasePrice(details.getBasePrice());
        return flightRepository.save(f);
    }

    /** Cancel all active bookings before deleting the flight */
    public void deleteFlight(String flightId) {
        bookingRepository.findByFlightId(flightId).forEach(b -> {
            if (b.getStatus() != BookingStatus.CANCELLED) {
                b.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(b);
            }
        });
        seatRepository.deleteAll(seatRepository.findByFlightFlightIdAndIsAvailableTrue(flightId));
        flightRepository.deleteById(flightId);
    }

    public List<Flight> searchFlights(String src, String dest, java.time.LocalDateTime date) {
        if (src == null || src.isBlank()) return flightRepository.findAll();
        return flightRepository.findBySourceAndDestinationAndDepartureTimeBetween(
            src, dest, date, date.plusDays(1));
    }

    public List<Flight> getFlightsBetween(LocalDate from, LocalDate to) {
        return flightRepository.findAll().stream()
            .filter(f -> f.getDepartureTime() != null
                && !f.getDepartureTime().toLocalDate().isBefore(from)
                && !f.getDepartureTime().toLocalDate().isAfter(to))
            .toList();
    }
}
````

## File: src/main/resources/application.properties
````
spring.application.name=airline-system
server.port=8081

# -------------------------------------------------------
# DB config: reads from application-local.properties (Railway)
# Falls back to Docker local DB if local file is absent
# -------------------------------------------------------
spring.datasource.url=jdbc:mysql://metro.proxy.rlwy.net:16010/railway?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=onoLCDyhrUtzMvTYCyBTnlARGgQzrIot
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# -------------------------------------------------------
# Disable Spring Security's auto-generated password log
# (SecurityConfig handles auth)
# -------------------------------------------------------
logging.level.org.springframework.security=WARN

# -------------------------------------------------------
# Jackson — parse/format LocalDateTime as ISO-8601 strings
# e.g. "2026-05-10T08:00:00" instead of numeric arrays
# Without this, POST /api/flights returns 400 Bad Request
# -------------------------------------------------------
spring.jackson.serialization.write-dates-as-timestamps=false
spring.jackson.deserialization.fail-on-unknown-properties=false
````

## File: src/main/resources/static/index.html
````html
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Airline Management System</title>
<style>
  * { box-sizing: border-box; margin: 0; padding: 0; }
  body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif; background: #f5f5f0; color: #1a1a1a; min-height: 100vh; }

  /* NAV */
  nav { background: #fff; border-bottom: 0.5px solid #ddd; padding: 0 2rem; display: flex; align-items: center; justify-content: space-between; height: 56px; position: sticky; top: 0; z-index: 100; }
  .nav-brand { font-size: 16px; font-weight: 500; display: flex; align-items: center; gap: 8px; }
  .nav-brand span { font-size: 20px; }
  .nav-tabs { display: flex; gap: 4px; }
  .tab-btn { padding: 6px 16px; border-radius: 20px; border: 0.5px solid transparent; background: transparent; cursor: pointer; font-size: 13px; color: #666; transition: all 0.15s; }
  .tab-btn:hover { background: #f5f5f0; color: #1a1a1a; }
  .tab-btn.active { background: #1a1a1a; color: #fff; border-color: #1a1a1a; }
  .nav-user { font-size: 13px; color: #666; display: flex; align-items: center; gap: 8px; }
  .user-badge { background: #f0f0f0; padding: 4px 10px; border-radius: 12px; font-size: 12px; }
  .user-badge.passenger { background: #e6f1fb; color: #185fa5; }
  .user-badge.staff { background: #eaf3de; color: #3b6d11; }
  .user-badge.admin { background: #faeeda; color: #854f0b; }

  /* LAYOUT */
  .page { display: none; padding: 2rem; max-width: 960px; margin: 0 auto; }
  .page.active { display: block; }

  /* CARDS */
  .card { background: #fff; border: 0.5px solid #ddd; border-radius: 12px; padding: 1.25rem; margin-bottom: 1rem; }
  .card-title { font-size: 15px; font-weight: 500; margin-bottom: 1rem; }
  .card-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; margin-bottom: 1.5rem; }
  .metric { background: #f5f5f0; border-radius: 8px; padding: 1rem; }
  .metric-label { font-size: 12px; color: #888; margin-bottom: 4px; }
  .metric-value { font-size: 22px; font-weight: 500; }

  /* FORMS */
  .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; margin-bottom: 12px; }
  .form-row.single { grid-template-columns: 1fr; }
  .form-row.triple { grid-template-columns: 1fr 1fr 1fr; }
  .field { display: flex; flex-direction: column; gap: 4px; }
  label { font-size: 12px; color: #666; font-weight: 500; }
  input, select { padding: 8px 12px; border: 0.5px solid #ddd; border-radius: 8px; font-size: 14px; background: #fff; color: #1a1a1a; outline: none; transition: border-color 0.15s; width: 100%; }
  input:focus, select:focus { border-color: #888; }
  .btn { padding: 8px 20px; border-radius: 8px; border: 0.5px solid #ddd; background: #1a1a1a; color: #fff; cursor: pointer; font-size: 13px; font-weight: 500; transition: all 0.15s; }
  .btn:hover { background: #333; }
  .btn.secondary { background: #fff; color: #1a1a1a; }
  .btn.secondary:hover { background: #f5f5f0; }
  .btn.danger { background: #a32d2d; border-color: #a32d2d; color: #fff; }
  .btn.danger:hover { background: #791f1f; }
  .btn.success { background: #3b6d11; border-color: #3b6d11; color: #fff; }
  .btn.success:hover { background: #27500a; }
  .btn-row { display: flex; gap: 8px; margin-top: 12px; }

  /* TABLE */
  .table-wrap { overflow-x: auto; }
  table { width: 100%; border-collapse: collapse; font-size: 13px; }
  th { text-align: left; padding: 8px 12px; font-weight: 500; font-size: 12px; color: #888; border-bottom: 0.5px solid #eee; }
  td { padding: 10px 12px; border-bottom: 0.5px solid #f0f0f0; color: #1a1a1a; }
  tr:last-child td { border-bottom: none; }
  tr:hover td { background: #fafafa; }

  /* STATUS BADGES */
  .badge { display: inline-block; padding: 2px 8px; border-radius: 10px; font-size: 11px; font-weight: 500; }
  .badge.confirmed { background: #eaf3de; color: #3b6d11; }
  .badge.pending { background: #faeeda; color: #854f0b; }
  .badge.cancelled { background: #fcebeb; color: #a32d2d; }
  .badge.scheduled { background: #e6f1fb; color: #185fa5; }
  .badge.delayed { background: #faeeda; color: #854f0b; }
  .badge.completed { background: #eaf3de; color: #3b6d11; }

  /* TOAST */
  #toast { position: fixed; bottom: 24px; right: 24px; background: #1a1a1a; color: #fff; padding: 10px 20px; border-radius: 8px; font-size: 13px; opacity: 0; transition: opacity 0.2s; pointer-events: none; z-index: 999; max-width: 320px; }
  #toast.show { opacity: 1; }
  #toast.error { background: #a32d2d; }
  #toast.success { background: #3b6d11; }

  /* SECTION HEADER */
  .section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem; }
  .section-title { font-size: 18px; font-weight: 500; }
  .section-sub { font-size: 13px; color: #888; margin-top: 2px; }

  /* LOGIN PAGE */
  .login-wrap { max-width: 400px; margin: 4rem auto; }
  .login-title { font-size: 22px; font-weight: 500; margin-bottom: 4px; }
  .login-sub { font-size: 14px; color: #888; margin-bottom: 1.5rem; }
  .role-pills { display: flex; gap: 8px; margin-bottom: 1.5rem; flex-wrap: wrap; }
  .role-pill { padding: 6px 16px; border-radius: 20px; border: 0.5px solid #ddd; background: #fff; cursor: pointer; font-size: 13px; color: #666; transition: all 0.15s; }
  .role-pill:hover { border-color: #888; }
  .role-pill.active { background: #1a1a1a; color: #fff; border-color: #1a1a1a; }

  /* EMPTY STATE */
  .empty { text-align: center; padding: 3rem 1rem; color: #aaa; font-size: 14px; }

  /* DIVIDER */
  hr { border: none; border-top: 0.5px solid #eee; margin: 1.25rem 0; }

  /* RESPONSIVE */
  @media (max-width: 600px) {
    .form-row { grid-template-columns: 1fr; }
    .form-row.triple { grid-template-columns: 1fr; }
    nav { padding: 0 1rem; }
    .page { padding: 1rem; }
  }
</style>
</head>
<body>

<nav>
  <div class="nav-brand"><span>✈</span> AirlineMS</div>
  <div class="nav-tabs" id="navTabs" style="display:none">
    <button class="tab-btn" onclick="showPage('dashboard')">Dashboard</button>
    <button class="tab-btn" id="passengerTab" onclick="showPage('passenger')" style="display:none">Bookings</button>
    <button class="tab-btn" id="staffTab" onclick="showPage('staff')" style="display:none">Flights</button>
    <button class="tab-btn" id="adminTab" onclick="showPage('admin')" style="display:none">Admin</button>
  </div>
  <div class="nav-user">
    <span id="navUserName" style="display:none"></span>
    <span id="navRoleBadge" class="user-badge" style="display:none"></span>
    <button class="btn secondary" id="logoutBtn" onclick="logout()" style="display:none; padding:4px 12px; font-size:12px;">Logout</button>
  </div>
</nav>

<!-- LOGIN PAGE -->
<div class="page active" id="loginPage">
  <div class="login-wrap">
    <div class="card" id="loginCard">
      <p class="login-title">Welcome back</p>
      <p class="login-sub">Sign in to your account</p>

      <div class="role-pills">
        <button class="role-pill active" onclick="selectRole('PASSENGER', this)">Passenger</button>
        <button class="role-pill" onclick="selectRole('STAFF', this)">Staff</button>
        <button class="role-pill" onclick="selectRole('ADMIN', this)">Admin</button>
      </div>

      <div class="field" style="margin-bottom:10px">
        <label>Username</label>
        <input type="text" id="loginUsername" placeholder="Enter username" />
      </div>
      <div class="field" style="margin-bottom:16px">
        <label>Password</label>
        <input type="password" id="loginPassword" placeholder="Enter password" />
      </div>
      <div class="btn-row">
        <button class="btn" onclick="doLogin()">Sign in</button>
        <button class="btn secondary" onclick="showRegisterForm()">Create account</button>
      </div>
    </div>

    <!-- Register form (hidden by default) -->
    <div class="card" id="registerCard" style="display:none; margin-top:16px">
      <p class="login-title" style="font-size:16px">Create account</p>
      <p class="login-sub" style="margin-bottom:12px">Registering as: <strong id="registerRoleLabel">Passenger</strong></p>
      <div class="field"><label>Username</label><input type="text" id="regUsername" placeholder="Choose a username" /></div>
      <div class="field"><label>Email</label><input type="email" id="regEmail" placeholder="your@email.com" /></div>
      <div class="field"><label>Phone</label><input type="tel" id="regPhone" placeholder="+91 9876543210" /></div>
      <div class="field"><label>Password</label><input type="password" id="regPassword" placeholder="Choose a password" /></div>
      <div class="btn-row" style="margin-top:12px">
        <button class="btn" onclick="doRegister()">Register</button>
        <button class="btn secondary" onclick="hideRegisterForm()">Cancel</button>
      </div>
    </div>
  </div>
</div>

<!-- DASHBOARD PAGE -->
<div class="page" id="dashboardPage">
  <div class="section-header">
    <div>
      <p class="section-title" id="dashGreeting">Hello</p>
    </div>
    <button class="btn secondary" onclick="loadDashboard()">Refresh</button>
  </div>
  <div class="card-grid" id="dashMetrics">
    <div class="metric"><div class="metric-label">Total flights</div><div class="metric-value" id="metricFlights">—</div></div>
    <div class="metric"><div class="metric-label">My bookings</div><div class="metric-value" id="metricBookings">—</div></div>
    <div class="metric"><div class="metric-label">Available seats</div><div class="metric-value" id="metricSeats">—</div></div>
  </div>

  <div class="card">
    <p class="card-title">Recent flights</p>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Flight</th><th>Route</th><th>Departure</th><th>Seats</th><th>Price</th><th>Status</th></tr></thead>
        <tbody id="dashFlightsTable"><tr><td colspan="6" class="empty">Loading...</td></tr></tbody>
      </table>
    </div>
  </div>
</div>

<!-- PASSENGER PAGE -->
<div class="page" id="passengerPage">
  <div class="section-header">
    <div><p class="section-title">Book a flight</p><p class="section-sub">Search and book available flights</p></div>
  </div>

  <!-- Search -->
  <div class="card">
    <p class="card-title">Search flights</p>
    <div class="form-row triple">
      <div class="field"><label>From</label><input id="searchSrc" placeholder="e.g. Bangalore" /></div>
      <div class="field"><label>To</label><input id="searchDest" placeholder="e.g. Mumbai" /></div>
      <div class="field"><label>Date</label><input type="date" id="searchDate" /></div>
    </div>
    <div class="btn-row">
      <button class="btn" onclick="searchFlights()">Search</button>
      <button class="btn secondary" onclick="loadAllFlights()">Show all flights</button>
    </div>
  </div>

  <!-- Search results -->
  <div class="card" id="searchResultsCard" style="display:none">
    <p class="card-title">Available flights</p>
    <div class="table-wrap">
      <table>
        <thead><tr><th>Flight</th><th>Airline</th><th>Route</th><th>Departure</th><th>Seats</th><th>Price</th><th></th></tr></thead>
        <tbody id="searchResultsTable"></tbody>
      </table>
    </div>
  </div>

  <!-- Book form -->
  <div class="card" id="bookFormCard" style="display:none">
    <p class="card-title">Book flight — <span id="bookFlightLabel" style="color:#888; font-weight:400"></span></p>
    <div class="form-row">
      <div class="field">
        <label>Seat class</label>
        <select id="bookSeatType" onchange="onSeatClassChange()">
          <option value="ECONOMY">Economy</option>
          <option value="BUSINESS">Business</option>
          <option value="FIRST_CLASS">First class</option>
        </select>
      </div>
      <div class="field"><label>Passengers</label><input type="number" id="bookCount" min="1" max="9" value="1" onchange="updateSeatSelectionLimit()" /></div>
    </div>

    <!-- Seat map -->
    <div id="seatMapSection" style="margin-top:1rem">
      <p style="font-size:13px; font-weight:500; margin-bottom:8px">Select your seat(s)
        <span style="font-size:11px; color:#888; font-weight:400" id="seatSelectHint"> — pick 1</span>
      </p>
      <div style="display:flex; gap:16px; margin-bottom:10px; font-size:11px; color:#888">
        <span><span style="display:inline-block;width:14px;height:14px;background:#e6f1fb;border:1px solid #b3d0f0;border-radius:3px;vertical-align:middle;margin-right:4px"></span>Available</span>
        <span><span style="display:inline-block;width:14px;height:14px;background:#1a1a1a;border:1px solid #1a1a1a;border-radius:3px;vertical-align:middle;margin-right:4px"></span>Selected</span>
        <span><span style="display:inline-block;width:14px;height:14px;background:#eee;border:1px solid #ccc;border-radius:3px;vertical-align:middle;margin-right:4px"></span>Taken</span>
      </div>
      <div id="seatMapLoading" style="font-size:13px;color:#aaa;padding:1rem 0">Loading seats...</div>
      <div id="seatMapGrid" style="display:grid; gap:4px; max-height:280px; overflow-y:auto; padding:4px 0"></div>
      <p style="font-size:12px; color:#888; margin-top:8px">Selected: <span id="selectedSeatNums" style="color:#1a1a1a; font-weight:500">None</span></p>
    </div>

    <div class="btn-row" style="margin-top:1rem">
      <button class="btn success" onclick="confirmBooking()">Confirm booking</button>
      <button class="btn secondary" onclick="document.getElementById('bookFormCard').style.display='none'; selectedSeatIds=[];">Cancel</button>
    </div>
  </div>

  <hr>
  <div class="section-header">
    <div><p class="section-title">My bookings</p></div>
    <button class="btn secondary" onclick="loadMyBookings()">Refresh</button>
  </div>
  <div class="card">
    <div class="table-wrap">
      <table>
        <thead><tr><th>PNR</th><th>Flight</th><th>Route</th><th>Departure</th><th>Class</th><th>Seats</th><th>Status</th><th>Action</th></tr></thead>
        <tbody id="myBookingsTable"><tr><td colspan="8" class="empty">No bookings yet</td></tr></tbody>
      </table>
    </div>
  </div>
</div>

<!-- STAFF PAGE -->
<div class="page" id="staffPage">
  <div class="section-header">
    <div><p class="section-title">Flight management</p><p class="section-sub">Add and manage flights</p></div>
  </div>

  <div class="card">
    <p class="card-title">Add new flight</p>
    <div class="form-row triple">
      <div class="field"><label>Flight number</label><input id="fNum" placeholder="e.g. AI101" /></div>
      <div class="field"><label>Airline</label><input id="fAirline" placeholder="e.g. Air India" /></div>
      <div class="field"><label>Base price (₹)</label><input type="number" id="fPrice" placeholder="3500" /></div>
    </div>
    <div class="form-row">
      <div class="field"><label>Source</label><input id="fSrc" placeholder="Bangalore" /></div>
      <div class="field"><label>Destination</label><input id="fDest" placeholder="Mumbai" /></div>
    </div>
    <div class="form-row">
      <div class="field"><label>Departure</label><input type="datetime-local" id="fDep" /></div>
      <div class="field"><label>Arrival</label><input type="datetime-local" id="fArr" /></div>
    </div>
    <div class="form-row">
      <div class="field"><label>Total seats</label><input type="number" id="fTotalSeats" placeholder="180" /></div>
      <div class="field"><label>Available seats</label><input type="number" id="fAvailSeats" placeholder="180" /></div>
    </div>
    <div class="form-row single">
      <div class="field">
        <label>Flight type</label>
        <select id="fType">
          <option value="DOMESTIC">Domestic</option>
          <option value="INTERNATIONAL">International</option>
        </select>
      </div>
    </div>
    <div class="btn-row">
      <button class="btn success" onclick="addFlight()">Add flight</button>
    </div>
  </div>

  <div class="section-header" style="margin-top:1.5rem">
    <p class="section-title">All flights</p>
    <button class="btn secondary" onclick="loadStaffFlights()">Refresh</button>
  </div>
  <div class="card">
    <div class="table-wrap">
      <table>
        <thead><tr><th>Flight</th><th>Airline</th><th>Route</th><th>Departure</th><th>Seats</th><th>Price</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody id="staffFlightsTable"><tr><td colspan="8" class="empty">Loading...</td></tr></tbody>
      </table>
    </div>
  </div>
</div>

<!-- ADMIN PAGE -->
<div class="page" id="adminPage">
  <div class="section-header">
    <div><p class="section-title">Admin panel</p><p class="section-sub">Reports and user management</p></div>
  </div>

  <div class="card">
    <p class="card-title">Generate reports</p>
    <div class="form-row triple">
      <div class="field"><label>Report type</label>
        <select id="reportType">
          <option value="revenue">Revenue report</option>
          <option value="cancellations">Cancellation report</option>
        </select>
      </div>
      <div class="field"><label>From date</label><input type="date" id="reportFrom" /></div>
      <div class="field"><label>To date</label><input type="date" id="reportTo" /></div>
    </div>
    <div class="btn-row"><button class="btn" onclick="generateReport()">Generate</button></div>
  </div>

  <div class="card" id="reportResultCard" style="display:none">
    <p class="card-title">Report results</p>
    <div class="card-grid" id="reportMetrics"></div>
    <div class="table-wrap" id="reportTableWrap"></div>
  </div>

  <div class="section-header" style="margin-top:1.5rem">
    <p class="section-title">All users</p>
    <button class="btn secondary" onclick="loadUsers()">Refresh</button>
  </div>
  <div class="card">
    <div class="table-wrap">
      <table>
        <thead><tr><th>Username</th><th>Email</th><th>Role</th><th>Status</th><th>Actions</th></tr></thead>
        <tbody id="usersTable"><tr><td colspan="5" class="empty">Loading...</td></tr></tbody>
      </table>
    </div>
  </div>
</div>
<!-- PAYMENT MODAL -->
<div id="paymentModal" style="display:none; position:fixed; inset:0; background:rgba(0,0,0,0.4); z-index:200; align-items:center; justify-content:center;">
  <div style="background:#fff; border-radius:12px; padding:1.5rem; width:420px; max-width:90vw; border:0.5px solid #ddd;">
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:1.25rem;">
      <p style="font-size:16px; font-weight:500;">Complete payment</p>
      <button onclick="closePaymentModal()" style="background:none;border:none;cursor:pointer;font-size:18px;color:#888;">✕</button>
    </div>

    <div style="background:#f5f5f0; border-radius:8px; padding:12px; margin-bottom:1rem; font-size:13px;">
      <span style="color:#888;">PNR:</span>
      <strong id="payPnr" style="margin-left:8px;">—</strong>
    </div>

    <input type="hidden" id="payBookingId" />

    <div class="field" style="margin-bottom:12px;">
      <label>Amount (₹)</label>
      <input type="number" id="payAmount" placeholder="Enter amount" min="0" />
    </div>

    <div class="field" style="margin-bottom:16px;">
      <label>Payment method</label>
      <select id="payMethod">
        <option value="UPI">UPI</option>
        <option value="CREDIT_CARD">Credit card</option>
        <option value="DEBIT_CARD">Debit card</option>
        <option value="NET_BANKING">Net banking</option>
      </select>
    </div>

    <div style="display:flex; gap:8px;">
      <button class="btn success" style="flex:1;" onclick="processPayment()">Pay now</button>
      <button class="btn secondary" onclick="closePaymentModal()">Pay later</button>
    </div>

    <p style="font-size:11px; color:#aaa; text-align:center; margin-top:12px;">This is a demo — no real payment is processed</p>
  </div>
</div>
<div id="toast"></div>

<script>
const API = 'http://localhost:8081';
let currentUser = null;
let selectedRole = 'PASSENGER';
let bookingFlightId = null;
let selectedSeatIds = [];   // seat IDs chosen in the seat map

function selectRole(role, el) {
  selectedRole = role;
  document.querySelectorAll('.role-pill').forEach(p => p.classList.remove('active'));
  el.classList.add('active');
  const label = document.getElementById('registerRoleLabel');
  if (label) label.textContent = role.charAt(0) + role.slice(1).toLowerCase();
}

async function api(method, path, body) {
  try {
    const opts = { method, headers: { 'Content-Type': 'application/json' } };
    if (body) opts.body = JSON.stringify(body);
    const res = await fetch(API + path, opts);
    const text = await res.text();
    let data;
    try { data = JSON.parse(text); } catch { data = text; }
    if (!res.ok) throw new Error(typeof data === 'string' ? data : (data.message || data.error || JSON.stringify(data)));
    return data;
  } catch (e) {
    if (e.name === 'TypeError' && e.message.includes('fetch')) {
      throw new Error('Cannot connect to server. Make sure Spring Boot is running on port 8081.');
    }
    throw e;
  }
}

function toast(msg, type = '') {
  const t = document.getElementById('toast');
  t.textContent = msg;
  t.className = 'show' + (type ? ' ' + type : '');
  clearTimeout(t._timer);
  t._timer = setTimeout(() => t.className = '', 3000);
}

function fmt(dt) {
  if (!dt) return '—';
  return new Date(dt).toLocaleString('en-IN', { day: '2-digit', month: 'short', year: 'numeric', hour: '2-digit', minute: '2-digit' });
}

function fmtDate(dt) {
  if (!dt) return '—';
  return new Date(dt).toLocaleDateString('en-IN', { day: '2-digit', month: 'short', year: 'numeric' });
}

function badge(status) {
  const s = (status || '').toLowerCase();
  return `<span class="badge ${s}">${status}</span>`;
}

function showPage(name) {
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
  document.getElementById(name + 'Page').classList.add('active');
  const tabs = { dashboard: 0, passenger: 1, staff: 2, admin: 3 };
  const allTabs = document.querySelectorAll('.tab-btn');
  if (allTabs[tabs[name]]) allTabs[tabs[name]].classList.add('active');

  if (name === 'dashboard') loadDashboard();
  if (name === 'passenger') { loadAllFlights(); loadMyBookings(); }
  if (name === 'staff') loadStaffFlights();
  if (name === 'admin') loadUsers();
}

function showRegisterForm() {
  document.getElementById('registerCard').style.display = 'block';
  document.getElementById('registerRoleLabel').textContent =
    selectedRole.charAt(0) + selectedRole.slice(1).toLowerCase();
  document.getElementById('regUsername').focus();
}
function hideRegisterForm() {
  document.getElementById('registerCard').style.display = 'none';
  ['regUsername','regEmail','regPhone','regPassword'].forEach(id =>
    (document.getElementById(id).value = ''));
}
async function doRegister() {
  const username = document.getElementById('regUsername').value.trim();
  const email    = document.getElementById('regEmail').value.trim();
  const phone    = document.getElementById('regPhone').value.trim();
  const password = document.getElementById('regPassword').value.trim();
  if (!username || !email || !password)
    return toast('Username, email and password are required', 'error');
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email))
    return toast('Please enter a valid email address', 'error');
  try {
    await api('POST', '/api/auth/register', { username, email, phone, password, role: selectedRole });
    toast('Account created! Please sign in.', 'success');
    hideRegisterForm();
    document.getElementById('loginUsername').value = username;
    document.getElementById('loginPassword').value = password;
  } catch (e) { toast(e.message, 'error'); }
}
async function doLogin() {
  const username = document.getElementById('loginUsername').value.trim();
  const password = document.getElementById('loginPassword').value.trim();

  if (!username || !password) {
    toast('Enter username and password', 'error');
    return;
  }

  try {
    // Fix: send role so backend can do username+role lookup (avoids NonUniqueResultException
    // when the same username exists under multiple roles in the DB).
    const res = await api('POST', '/api/auth/login', { username, password, role: selectedRole });

    currentUser = {
      userId: res.userId,
      username: res.username,
      role: res.role,
      email: res.email
    };

    if (!currentUser.userId) {
      toast('Login failed: no user ID returned', 'error');
      return;
    }

    afterLogin();
    toast('Welcome, ' + currentUser.username + '!', 'success');

  } catch (e) {
    toast(e.message, 'error');
  }
}

function afterLogin() {
  document.getElementById('loginPage').classList.remove('active');
  document.getElementById('navTabs').style.display = 'flex';
  document.getElementById('navUserName').textContent = currentUser.username;
  document.getElementById('navUserName').style.display = 'inline';
  document.getElementById('logoutBtn').style.display = 'inline-block';

  const roleBadge = document.getElementById('navRoleBadge');
  roleBadge.textContent = currentUser.role;
  roleBadge.className = 'user-badge ' + (currentUser.role || '').toLowerCase();
  roleBadge.style.display = 'inline-block';

  // Bug fix: Bookings tab only for PASSENGER
  if (currentUser.role === 'PASSENGER') {
    document.getElementById('passengerTab').style.display = 'inline-block';
  }
  // Flights tab for STAFF and ADMIN
  if (currentUser.role === 'STAFF' || currentUser.role === 'ADMIN') {
    document.getElementById('staffTab').style.display = 'inline-block';
  }
  if (currentUser.role === 'ADMIN') {
    document.getElementById('adminTab').style.display = 'inline-block';
  }

  showPage('dashboard');
  document.querySelectorAll('.tab-btn')[0].classList.add('active');
}

function logout() {
  currentUser = null;
  document.getElementById('navTabs').style.display = 'none';
  document.getElementById('navUserName').style.display = 'none';
  document.getElementById('navRoleBadge').style.display = 'none';
  document.getElementById('logoutBtn').style.display = 'none';
  document.getElementById('passengerTab').style.display = 'none';
  document.getElementById('staffTab').style.display = 'none';
  document.getElementById('adminTab').style.display = 'none';
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));
  document.getElementById('loginPage').classList.add('active');
  document.getElementById('loginUsername').value = '';
  document.getElementById('loginPassword').value = '';
  selectedRole = 'PASSENGER';
  document.querySelectorAll('.role-pill').forEach((p, i) => p.classList.toggle('active', i === 0));
}

async function loadDashboard() {
  document.getElementById('dashGreeting').textContent = 'Hello, ' + (currentUser ? currentUser.username : '') + '.';
  try {
    const flights = await api('GET', '/api/flights');
    document.getElementById('metricFlights').textContent = flights.length;
    const totalSeats = flights.reduce((sum, f) => sum + (f.availableSeats || 0), 0);
    document.getElementById('metricSeats').textContent = totalSeats.toLocaleString();

    const tbody = document.getElementById('dashFlightsTable');
    if (!flights.length) { tbody.innerHTML = '<tr><td colspan="6" class="empty">No flights yet</td></tr>'; return; }
    tbody.innerHTML = flights.slice(0, 6).map(f => `
      <tr>
        <td><strong>${f.flightNumber || '—'}</strong></td>
        <td>${f.source || '—'} → ${f.destination || '—'}</td>
        <td>${fmt(f.departureTime)}</td>
        <td>${f.availableSeats ?? '—'} / ${f.totalSeats ?? '—'}</td>
        <td>₹${(f.basePrice || 0).toLocaleString()}</td>
        <td>${badge(f.status || 'SCHEDULED')}</td>
      </tr>`).join('');
  } catch (e) { toast(e.message, 'error'); }

  if (currentUser && currentUser.userId) {
    try {
      const bookings = await api('GET', '/api/bookings/passenger/' + currentUser.userId);
      document.getElementById('metricBookings').textContent = Array.isArray(bookings) ? bookings.length : '—';
    } catch { document.getElementById('metricBookings').textContent = '0'; }
  } else {
    document.getElementById('metricBookings').textContent = '0';
  }
}

async function loadAllFlights() {
  try {
    const flights = await api('GET', '/api/flights');
    renderSearchResults(flights);
  } catch (e) { toast(e.message, 'error'); }
}

async function searchFlights() {
  const src = document.getElementById('searchSrc').value.trim();
  const dest = document.getElementById('searchDest').value.trim();
  const date = document.getElementById('searchDate').value;
  if (!src || !dest || !date) { toast('Fill in all search fields', 'error'); return; }
  try {
    const flights = await api('GET', `/api/flights/search?src=${encodeURIComponent(src)}&dest=${encodeURIComponent(dest)}&date=${date}T00:00:00`);
    renderSearchResults(flights);
    if (!flights.length) toast('No flights found for that route/date');
  } catch (e) { toast(e.message, 'error'); }
}

function renderSearchResults(flights) {
  const card = document.getElementById('searchResultsCard');
  const tbody = document.getElementById('searchResultsTable');
  card.style.display = 'block';
  if (!flights.length) { tbody.innerHTML = '<tr><td colspan="7" class="empty">No flights found</td></tr>'; return; }
  tbody.innerHTML = flights.map(f => `
    <tr>
      <td><strong>${f.flightNumber || '—'}</strong></td>
      <td>${f.airlineName || '—'}</td>
      <td>${f.source || '—'} → ${f.destination || '—'}</td>
      <td>${fmt(f.departureTime)}</td>
      <td>${f.availableSeats ?? '—'}</td>
      <td>₹${(f.basePrice || 0).toLocaleString()}</td>
      <td><button class="btn" style="padding:4px 12px;font-size:12px" onclick="openBookForm('${f.flightId}','${f.flightNumber} ${f.source}→${f.destination}')">Book</button></td>
    </tr>`).join('');
}

function openBookForm(flightId, label) {
  bookingFlightId = flightId;
  selectedSeatIds = [];
  document.getElementById('bookFlightLabel').textContent = label;
  document.getElementById('bookFormCard').style.display = 'block';
  document.getElementById('bookFormCard').scrollIntoView({ behavior: 'smooth' });
  document.getElementById('bookCount').value = 1;
  updateSeatSelectionLimit();
  loadSeatMap();
}

function onSeatClassChange() {
  selectedSeatIds = [];
  loadSeatMap();
}

function updateSeatSelectionLimit() {
  const n = parseInt(document.getElementById('bookCount').value) || 1;
  document.getElementById('seatSelectHint').textContent = ` — pick ${n}`;
  // deselect extras if limit reduced
  if (selectedSeatIds.length > n) {
    selectedSeatIds = selectedSeatIds.slice(0, n);
    refreshSeatMapSelection();
  }
}
function openPaymentModal(booking) {
  document.getElementById('payBookingId').value = booking.bookingId;
  document.getElementById('payPnr').textContent = booking.pnr;
  document.getElementById('payAmount').value = booking.totalAmount || '';
  document.getElementById('paymentModal').style.display = 'flex';
}

async function processPayment() {
  const bookingId = document.getElementById('payBookingId').value;
  const amount = parseFloat(document.getElementById('payAmount').value) || 0;
  const method = document.getElementById('payMethod').value;
  if (!amount || amount <= 0) { toast('Enter a valid amount', 'error'); return; }
  try {
    const payment = await api('POST', '/api/payments', { bookingId, amount, paymentMethod: method });
    toast('Payment successful! Transaction ID: ' + payment.transactionId, 'success');
    document.getElementById('paymentModal').style.display = 'none';
    loadMyBookings();
  } catch (e) { toast(e.message, 'error'); }
}

function closePaymentModal() {
  document.getElementById('paymentModal').style.display = 'none';
}

async function loadSeatMap() {
  const seatType = document.getElementById('bookSeatType').value;
  const grid = document.getElementById('seatMapGrid');
  document.getElementById('seatMapLoading').style.display = 'block';
  grid.innerHTML = '';
  document.getElementById('selectedSeatNums').textContent = 'None';
  selectedSeatIds = [];

  try {
    // Use /all so we get taken seats too — needed to grey them out
    const allSeats = await api('GET', `/api/seats/${bookingFlightId}/all`);
    document.getElementById('seatMapLoading').style.display = 'none';

    if (!allSeats || allSeats.length === 0) {
      grid.innerHTML = '<p style="font-size:13px;color:#aaa;padding:8px 0">No seat data yet — seats will be auto-assigned on booking.</p>';
      return;
    }

    // Filter to chosen class
    const typeSeats = allSeats.filter(s => s.seatType === seatType);
    if (!typeSeats.length) {
      grid.innerHTML = '<p style="font-size:13px;color:#aaa;padding:8px 0">No seats in this class.</p>';
      return;
    }

    // Determine unique sorted rows and columns
    const rows = [...new Set(typeSeats.map(s => s.row))].sort((a, b) => a - b);
    const cols = [...new Set(typeSeats.map(s => s.seatColumn))].sort();

    // Class colours
    const classColour = { ECONOMY: '#2563eb', BUSINESS: '#7c3aed', FIRST_CLASS: '#b45309' };
    const classBg     = { ECONOMY: '#eff6ff', BUSINESS: '#f5f3ff', FIRST_CLASS: '#fffbeb' };
    const classBorder = { ECONOMY: '#bfdbfe', BUSINESS: '#ddd6fe', FIRST_CLASS: '#fde68a' };
    const c  = classColour[seatType] || '#2563eb';
    const bg = classBg[seatType]     || '#eff6ff';
    const bd = classBorder[seatType] || '#bfdbfe';

    // Build lookup by "row-col"
    const byPos = {};
    typeSeats.forEach(s => { byPos[`${s.row}-${s.seatColumn}`] = s; });

    // Find the aisle gap: ECONOMY/BUSINESS = after col C | FIRST_CLASS = after col B
    const aisleAfter = seatType === 'FIRST_CLASS' ? 'B' : 'C';

    // ── Cabin wrapper ────────────────────────────────────────
    grid.style.display    = 'block';
    grid.style.overflowX  = 'auto';
    grid.style.paddingBottom = '8px';

    let html = `
      <div style="display:inline-block;min-width:320px;font-family:inherit">
        <!-- nose icon -->
        <div style="text-align:center;margin-bottom:6px;font-size:22px;color:#94a3b8">✈</div>

        <!-- column header row -->
        <div style="display:flex;align-items:center;gap:4px;margin-bottom:4px;padding-left:32px">`;

    cols.forEach((col, i) => {
      if (i > 0 && cols[i - 1] === aisleAfter)
        html += `<div style="width:20px"></div>`;   // aisle gap
      html += `<div style="width:34px;text-align:center;font-size:11px;font-weight:600;color:#64748b">${col}</div>`;
    });
    html += `</div>`;

    // ── Seat rows ────────────────────────────────────────────
    rows.forEach(row => {
      html += `<div style="display:flex;align-items:center;gap:4px;margin-bottom:4px">`;
      // row number label
      html += `<div style="width:28px;text-align:right;font-size:11px;color:#94a3b8;padding-right:4px">${row}</div>`;

      cols.forEach((col, i) => {
        if (i > 0 && cols[i - 1] === aisleAfter)
          html += `<div style="width:20px"></div>`;   // aisle gap

        const seat = byPos[`${row}-${col}`];
        if (!seat) {
          html += `<div style="width:34px"></div>`;
          return;
        }
        const avail   = seat.available;
        const seatBg  = avail ? bg    : '#e2e8f0';
        const seatBd  = avail ? bd    : '#cbd5e1';
        const seatCol = avail ? c     : '#94a3b8';
        const cursor  = avail ? 'pointer' : 'not-allowed';
        const onclick = avail ? `toggleSeat('${seat.seatId}','${seat.seatNumber}')` : '';
        const title   = `${seat.seatNumber} — ${avail ? 'Available ₹' + Math.round(seat.price) : 'Taken'}`;

        html += `
          <div id="seat-${seat.seatId}"
               data-seatid="${seat.seatId}"
               data-seatnum="${seat.seatNumber}"
               data-avail="${avail}"
               onclick="${onclick}"
               title="${title}"
               style="
                 width:34px;height:32px;border-radius:6px 6px 4px 4px;
                 font-size:10px;font-weight:600;
                 display:flex;align-items:center;justify-content:center;
                 cursor:${cursor};
                 border:1.5px solid ${seatBd};
                 background:${seatBg};
                 color:${seatCol};
                 box-shadow:${avail ? '0 1px 3px rgba(0,0,0,.08)' : 'none'};
                 transition:all .12s;
                 position:relative;
               "
          >${seat.seatNumber}</div>`;
      });

      html += `</div>`;  // end row
    });

    html += `</div>`;  // end cabin wrapper
    grid.innerHTML = html;

  } catch (e) {
    document.getElementById('seatMapLoading').style.display = 'none';
    grid.innerHTML = '<p style="font-size:13px;color:#aaa;padding:8px 0">Could not load seat map.</p>';
    console.error(e);
  }
}

function toggleSeat(seatId, seatNum) {
  const limit = parseInt(document.getElementById('bookCount').value) || 1;
  const idx   = selectedSeatIds.indexOf(seatId);
  if (idx >= 0) {
    selectedSeatIds.splice(idx, 1);
  } else {
    if (selectedSeatIds.length >= limit) {
      toast(`Select exactly ${limit} seat(s) — one per passenger`, 'error');
      return;
    }
    selectedSeatIds.push(seatId);
  }
  refreshSeatMapSelection();
}

function refreshSeatMapSelection() {
  const seatType  = document.getElementById('bookSeatType').value;
  const classColour = { ECONOMY: '#2563eb', BUSINESS: '#7c3aed', FIRST_CLASS: '#b45309' };
  const classBg     = { ECONOMY: '#eff6ff', BUSINESS: '#f5f3ff', FIRST_CLASS: '#fffbeb' };
  const classBorder = { ECONOMY: '#bfdbfe', BUSINESS: '#ddd6fe', FIRST_CLASS: '#fde68a' };
  const c  = classColour[seatType] || '#2563eb';
  const bg = classBg[seatType]     || '#eff6ff';
  const bd = classBorder[seatType] || '#bfdbfe';

  document.querySelectorAll('[data-seatid]').forEach(el => {
    if (el.dataset.avail !== 'true') return;   // leave taken seats alone
    const isSelected = selectedSeatIds.includes(el.dataset.seatid);
    el.style.background   = isSelected ? '#1e293b' : bg;
    el.style.color        = isSelected ? '#f8fafc'  : c;
    el.style.borderColor  = isSelected ? '#0f172a'  : bd;
    el.style.boxShadow    = isSelected ? '0 0 0 2px #0f172a40' : '0 1px 3px rgba(0,0,0,.08)';
    el.style.transform    = isSelected ? 'scale(1.08)' : 'scale(1)';
  });

  const nums = selectedSeatIds.map(id => {
    const el = document.querySelector(`[data-seatid="${id}"]`);
    return el ? el.dataset.seatnum : id;
  });
  document.getElementById('selectedSeatNums').textContent = nums.length ? nums.join(', ') : 'None';
}

async function confirmBooking() {
  if (!currentUser || !currentUser.userId) {
    toast('Could not find your user ID. Try logging out and back in.', 'error'); return;
  }
  const seatType = document.getElementById('bookSeatType').value;
  const count = parseInt(document.getElementById('bookCount').value);
  if (selectedSeatIds.length > 0 && selectedSeatIds.length < count) {
    toast(`Please select exactly ${count} seat(s), or deselect all for auto-assign.`, 'error'); return;
  }
  try {
    const booking = await api('POST', '/api/bookings', {
      flightId: bookingFlightId,
      passengerId: currentUser.userId,
      seatType, passengerCount: count,
      seatIds: selectedSeatIds.length ? selectedSeatIds : null
    });
    toast('Booking confirmed! PNR: ' + booking.pnr + ' — Please complete payment.', 'success');
    selectedSeatIds = [];
    document.getElementById('bookFormCard').style.display = 'none';
    openPaymentModal(booking);  // ← trigger payment
    loadMyBookings();
    loadDashboard();
  } catch (e) { toast(e.message, 'error'); }
}

async function loadMyBookings() {
  if (!currentUser || !currentUser.userId) { return; }
  try {
    const bookings = await api('GET', '/api/bookings/passenger/' + currentUser.userId + '/details');
    const tbody = document.getElementById('myBookingsTable');
    if (!Array.isArray(bookings) || !bookings.length) {
      tbody.innerHTML = '<tr><td colspan="8" class="empty">No bookings yet</td></tr>'; return;
    }
    tbody.innerHTML = bookings.map(b => `
      <tr>
        <td><strong>${b.pnr || '—'}</strong></td>
        <td>${b.flightNumber || '—'}<br><span style="font-size:11px;color:#888">${b.airlineName || ''}</span></td>
        <td>${b.source || '—'} → ${b.destination || '—'}</td>
        <td>${fmt(b.departureTime)}</td>
        <td>${b.seatType || '—'}</td>
        <td style="font-size:12px">${b.seatNumbers && b.seatNumbers.length ? b.seatNumbers.join(', ') : `${b.numberOfPassengers} pax`}</td>
        <td>${badge(b.status)}</td>
        <td>${b.status !== 'CANCELLED' ? `<button class="btn danger" style="padding:3px 10px;font-size:12px" onclick="cancelBooking('${b.bookingId}')">Cancel</button>` : '—'}</td>
      </tr>`).join('');
  } catch (e) { toast(e.message, 'error'); }
}

async function cancelBooking(bookingId) {
  if (!confirm('Cancel this booking?')) return;
  try {
    await api('PUT', '/api/bookings/' + bookingId + '/cancel');
    toast('Booking cancelled', 'success');
    loadMyBookings();
  } catch (e) { toast(e.message, 'error'); }
}

async function loadStaffFlights() {
  try {
    const flights = await api('GET', '/api/flights');
    const tbody = document.getElementById('staffFlightsTable');
    if (!flights.length) { tbody.innerHTML = '<tr><td colspan="8" class="empty">No flights yet</td></tr>'; return; }
    tbody.innerHTML = flights.map(f => `
      <tr>
        <td><strong>${f.flightNumber || '—'}</strong></td>
        <td>${f.airlineName || '—'}</td>
        <td>${f.source || '—'} → ${f.destination || '—'}</td>
        <td>${fmt(f.departureTime)}</td>
        <td>${f.availableSeats ?? '—'} / ${f.totalSeats ?? '—'}</td>
        <td>₹${(f.basePrice || 0).toLocaleString()}</td>
        <td>${badge(f.status || 'SCHEDULED')}</td>
        <td><button class="btn danger" style="padding:3px 10px;font-size:12px" onclick="deleteFlight('${f.flightId}')">Delete</button></td>
      </tr>`).join('');
  } catch (e) { toast(e.message, 'error'); }
}

async function addFlight() {
  const body = {
    flightNumber: document.getElementById('fNum').value.trim(),
    airlineName: document.getElementById('fAirline').value.trim(),
    source: document.getElementById('fSrc').value.trim(),
    destination: document.getElementById('fDest').value.trim(),
    departureTime: document.getElementById('fDep').value,
    arrivalTime: document.getElementById('fArr').value,
    basePrice: parseFloat(document.getElementById('fPrice').value) || 0,
    totalSeats: parseInt(document.getElementById('fTotalSeats').value) || 0,
    availableSeats: parseInt(document.getElementById('fAvailSeats').value) || 0,
    status: 'SCHEDULED',
    type: document.getElementById('fType').value   // ← ADD THIS LINE
};
  if (!body.flightNumber || !body.source || !body.destination || !body.departureTime) {
    toast('Fill in all required fields', 'error'); return;
  }
  try {
    await api('POST', '/api/flights', body);
    toast('Flight added successfully!', 'success');
    ['fNum','fAirline','fSrc','fDest','fDep','fArr','fPrice','fTotalSeats','fAvailSeats'].forEach(id => document.getElementById(id).value = '');
    loadStaffFlights();
    loadDashboard();
  } catch (e) { toast(e.message, 'error'); }
}

async function deleteFlight(flightId) {
  if (!confirm('Delete this flight? This cannot be undone.')) return;
  try {
    await api('DELETE', '/api/flights/' + flightId);
    toast('Flight deleted', 'success');
    loadStaffFlights();
  } catch (e) { toast(e.message, 'error'); }
}

async function loadUsers() {
  try {
    const users = await api('GET', '/api/auth/users');
    const tbody = document.getElementById('usersTable');
    if (!Array.isArray(users) || !users.length) {
      tbody.innerHTML = '<tr><td colspan="5" class="empty">No users found</td></tr>'; return;
    }
    tbody.innerHTML = users.map(u => `
      <tr>
        <td><strong>${u.username || '—'}</strong></td>
        <td>${u.email || '—'}</td>
        <td>${badge(u.role)}</td>
        <td>${u.active ? '<span class="badge confirmed">Active</span>' : '<span class="badge cancelled">Inactive</span>'}</td>
        <td>
          <button class="btn secondary" style="padding:3px 10px;font-size:12px" onclick="toggleUser('${u.userId}', ${u.active})">${u.active ? 'Deactivate' : 'Activate'}</button>
        </td>
      </tr>`).join('');
  } catch (e) { toast(e.message, 'error'); }
}

async function toggleUser(userId, isActive) {
  try {
    await api('PUT', '/api/auth/users/' + userId + '/status?active=' + !isActive);
    toast('User status updated', 'success');
    loadUsers();
  } catch (e) { toast(e.message, 'error'); }
}

async function generateReport() {
  const type = document.getElementById('reportType').value;
  const from = document.getElementById('reportFrom').value;
  const to = document.getElementById('reportTo').value;
  if (!from || !to) { toast('Select a date range', 'error'); return; }
  try {
    const data = await api('GET', `/api/reports/${type}?from=${from}&to=${to}`);
    const card = document.getElementById('reportResultCard');
    card.style.display = 'block';
    const metrics = document.getElementById('reportMetrics');
    const tableWrap = document.getElementById('reportTableWrap');

    if (type === 'revenue') {
      metrics.innerHTML = `
        <div class="metric"><div class="metric-label">Total revenue</div><div class="metric-value">₹${(data.totalRevenue || 0).toLocaleString()}</div></div>
        <div class="metric"><div class="metric-label">Total bookings</div><div class="metric-value">${data.totalBookings || 0}</div></div>
        <div class="metric"><div class="metric-label">Flights operated</div><div class="metric-value">${data.totalFlights || 0}</div></div>`;

      const bookings = data.bookings || [];
      tableWrap.innerHTML = bookings.length ? `
        <table><thead><tr><th>PNR</th><th>Flight</th><th>Class</th><th>Pax</th><th>Date</th><th>Status</th></tr></thead>
        <tbody>${bookings.map(b => `<tr>
          <td><strong>${b.pnr || '—'}</strong></td>
          <td>${b.flightId ? b.flightId.substring(0,8) + '...' : '—'}</td>
          <td>${b.seatType || '—'}</td>
          <td>${b.numberOfPassengers || 1}</td>
          <td>${fmtDate(b.bookingDate)}</td>
          <td>${badge(b.status)}</td>
        </tr>`).join('')}</tbody></table>` : '<p class="empty">No bookings in this range</p>';
    } else {
      const cancelled = data.cancelledBookings || [];
      metrics.innerHTML = `<div class="metric"><div class="metric-label">Total cancellations</div><div class="metric-value">${data.totalCancellations || 0}</div></div>`;
      tableWrap.innerHTML = cancelled.length ? `
        <table><thead><tr><th>PNR</th><th>Flight</th><th>Class</th><th>Date</th></tr></thead>
        <tbody>${cancelled.map(b => `<tr>
          <td><strong>${b.pnr || '—'}</strong></td>
          <td>${b.flightId ? b.flightId.substring(0,8) + '...' : '—'}</td>
          <td>${b.seatType || '—'}</td>
          <td>${fmtDate(b.bookingDate)}</td>
        </tr>`).join('')}</tbody></table>` : '<p class="empty">No cancellations in this range</p>';
    }
    card.scrollIntoView({ behavior: 'smooth' });
    toast('Report generated', 'success');
  } catch (e) { toast(e.message, 'error'); }
}

document.getElementById('searchDate').valueAsDate = new Date();
const today = new Date().toISOString().split('T')[0];
document.getElementById('reportFrom').value = today.substring(0, 7) + '-01';
document.getElementById('reportTo').value = today;
</script>
</body>
</html>
````
