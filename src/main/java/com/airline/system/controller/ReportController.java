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
