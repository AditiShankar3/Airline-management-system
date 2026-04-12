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
