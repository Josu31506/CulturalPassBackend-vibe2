package com.culturalpass.culturalpass.Statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticsOverviewDto {
    private MonthlyUsersEnrolledDto monthlyUsersEnrolled;
    private PendingEventsDto pendingEvents;
    private YearlyEventsDto yearlyEvents;
    private MonthlyRevenueDto monthlyRevenue;
    private MonthlyEnrollmentRecordDto monthlyEnrollmentRecord;
}
