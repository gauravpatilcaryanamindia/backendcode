package com.spring.jwt.serviceStation.mapper;

import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingResponseDto;
import com.spring.jwt.serviceStation.entity.ServiceStationBooking;

public class BookingMapper {

    private BookingMapper() {
    }

    public static BookingResponseDto toResponseDto(ServiceStationBooking booking) {

        if (booking == null) {
            return null;
        }

        BookingResponseDto dto = new BookingResponseDto();

        // IDs
        dto.setBookingId(booking.getId());

        // Status & notes
        dto.setStatus(booking.getServiceStatus());
        dto.setNotes(booking.getServiceNotes());

        // Charges
        if (booking.getServiceCharges() != null) {
            dto.setCharges(booking.getServiceCharges().doubleValue());
        }

        // Dates
        dto.setCreatedAt(booking.getCreatedAt());
        dto.setUpdatedAt(booking.getUpdatedAt());
        dto.setServiceStartTime(booking.getServiceStartTime());
        dto.setServiceEndTime(booking.getServiceEndTime());

        return dto;
    }
}