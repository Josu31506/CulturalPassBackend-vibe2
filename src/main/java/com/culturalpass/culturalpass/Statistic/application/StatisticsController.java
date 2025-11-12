package com.culturalpass.culturalpass.Statistic.application;

import com.culturalpass.culturalpass.Statistic.domain.StatisticService;
import com.culturalpass.culturalpass.Statistic.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime ;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly-users-enrolled")
    public ResponseEntity<MonthlyUsersEnrolledDto> getMonthlyUsersEnrolled(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        OffsetDateTime  now = OffsetDateTime .now();
        Integer targetMonth = month != null ? month : now.getMonthValue();
        Integer targetYear = year != null ? year : now.getYear();

        MonthlyUsersEnrolledDto response = statisticService.getMonthlyUsersEnrolled(targetMonth, targetYear);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending-events")
    public ResponseEntity<PendingEventsDto> getPendingEvents() {
        PendingEventsDto response = statisticService.getPendingEvents();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/yearly-events")
    public ResponseEntity<YearlyEventsDto> getYearlyEvents(
            @RequestParam(required = false) Integer year) {

        Integer targetYear = year != null ? year : LocalDate.now().getYear();
        YearlyEventsDto response = statisticService.getYearlyEvents(targetYear);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly-revenue")
    public ResponseEntity<MonthlyRevenueDto> getMonthlyRevenue(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        LocalDate now = LocalDate.now();
        Integer targetMonth = month != null ? month : now.getMonthValue();
        Integer targetYear = year != null ? year : now.getYear();

        MonthlyRevenueDto response = statisticService.getMonthlyRevenue(targetMonth, targetYear);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly-enrollment-record")
    public ResponseEntity<MonthlyEnrollmentRecordDto> getMonthlyEnrollmentRecord(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        LocalDate now = LocalDate.now();
        Integer targetMonth = month != null ? month : now.getMonthValue();
        Integer targetYear = year != null ? year : now.getYear();

        MonthlyEnrollmentRecordDto response = statisticService.getMonthlyEnrollmentRecord(targetMonth, targetYear);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/overview")
    public ResponseEntity<StatisticsOverviewDto> getStatisticsOverview(
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        StatisticsOverviewDto response = statisticService.getStatisticsOverview(month, year);
        return ResponseEntity.ok(response);
    }
}
