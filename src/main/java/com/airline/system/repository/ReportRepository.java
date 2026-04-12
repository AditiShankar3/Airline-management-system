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
