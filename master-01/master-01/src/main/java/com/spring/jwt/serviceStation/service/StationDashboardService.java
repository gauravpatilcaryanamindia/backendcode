package com.spring.jwt.serviceStation.service;


import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.DashboardResponse;
import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.StationDashboardDto;

public interface StationDashboardService {

    StationDashboardDto getDashboard(Long stationId);

}