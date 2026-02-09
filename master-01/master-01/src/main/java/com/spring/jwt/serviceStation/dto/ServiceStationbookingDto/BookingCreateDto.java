package com.spring.jwt.serviceStation.dto.ServiceStationbookingDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookingCreateDto {

    private Long stationId;
    private String technicianName;
    private BigDecimal serviceCharges;
    private String serviceNotes;
    private String serviceStatus;
}

