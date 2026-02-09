package com.spring.jwt.serviceStation.controller;


import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.StationDashboardDto;
import com.spring.jwt.serviceStation.service.StationDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-stations")
@RequiredArgsConstructor
public class StationDashboardController {

    private final StationDashboardService dashboardService;

    @GetMapping("/{stationId}/dashboard")
    public StationDashboardDto getDashboard(@PathVariable("stationId") Long stationId) {
        return dashboardService.getDashboard(stationId);
    }
}