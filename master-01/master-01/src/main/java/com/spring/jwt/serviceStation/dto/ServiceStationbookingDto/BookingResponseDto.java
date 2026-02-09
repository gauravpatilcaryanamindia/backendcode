package com.spring.jwt.serviceStation.dto.ServiceStationbookingDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingResponseDto {

    private Long bookingId;
    private String status;
    private String notes;
    private Double charges;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 🔽 ADD THESE
    private LocalDateTime serviceStartTime;
    private LocalDateTime serviceEndTime;
}


