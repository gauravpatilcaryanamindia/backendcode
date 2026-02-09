package com.spring.jwt.serviceStation.dto.ServiceStationbookingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DashboardResponse {

    private long totalBookings;
    private long todayBookings;
    private long pending;
    private long inProgress;
    private long completed;
    private long rejected;
    private BigDecimal totalRevenue;
}
