package com.spring.jwt.serviceStation.service.impl;


import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.DashboardResponse;
import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.StationDashboardDto;
import com.spring.jwt.serviceStation.repository.ServiceStationBookingRepository;
import com.spring.jwt.serviceStation.service.StationDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StationDashboardServiceImpl
        implements StationDashboardService {

    private final ServiceStationBookingRepository bookingRepository;

    @Override
    public StationDashboardDto getDashboard(Long stationId) {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        long totalBookings =
                bookingRepository.countByServiceStation_Id(stationId);

        long todayBookings =
                bookingRepository.countTodayBookings(stationId, start, end);

        long pending =
                bookingRepository.countByServiceStation_IdAndServiceStatus(
                        stationId, "PENDING");

        long inProgress =
                bookingRepository.countByServiceStation_IdAndServiceStatus(
                        stationId, "IN_PROGRESS");

        long completed =
                bookingRepository.countByServiceStation_IdAndServiceStatus(
                        stationId, "COMPLETED");

        long rejected =
                bookingRepository.countByServiceStation_IdAndServiceStatus(
                        stationId, "REJECTED");

        BigDecimal totalRevenue =
                bookingRepository.calculateTotalRevenue(stationId);

        return new StationDashboardDto(
                totalBookings,
                todayBookings,
                pending,
                inProgress,
                completed,
              totalRevenue
        );
    }
}