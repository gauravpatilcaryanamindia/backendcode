package com.spring.jwt.serviceStation.service;

import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingCreateDto;
import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceStationBookingService {

    BookingResponseDto createBooking(Long stationId, BookingCreateDto dto);

    List<BookingResponseDto> getAllBookings(Long stationId);

    BookingResponseDto getBooking(Long stationId, Long bookingId);

    BookingResponseDto updateStatus(Long stationId, Long bookingId, String status);

    BookingResponseDto updateCharges(Long stationId, Long bookingId, BigDecimal amount);

    BookingResponseDto completeService(Long stationId, Long bookingId, String notes);

    void deleteBooking(Long stationId, Long bookingId);

}
