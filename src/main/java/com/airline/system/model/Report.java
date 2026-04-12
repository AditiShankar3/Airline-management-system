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
