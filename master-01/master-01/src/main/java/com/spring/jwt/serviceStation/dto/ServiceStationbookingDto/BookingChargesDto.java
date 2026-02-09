package com.spring.jwt.serviceStation.dto.ServiceStationbookingDto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookingChargesDto {
    private BigDecimal amount;
}
