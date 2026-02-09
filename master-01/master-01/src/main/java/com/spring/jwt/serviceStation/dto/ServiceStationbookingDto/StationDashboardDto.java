package com.spring.jwt.serviceStation.dto.ServiceStationbookingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StationDashboardDto {

    private long totalBookings;
    private long todayBookings;
    private long completedServices;
    private long pendingServices;
    private long rejectedServices;
    private BigDecimal totalRevenue;
}
