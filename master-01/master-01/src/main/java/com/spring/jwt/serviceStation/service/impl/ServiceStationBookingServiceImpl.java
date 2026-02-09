package com.spring.jwt.serviceStation.service.impl;

import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingCreateDto;
import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingResponseDto;
import com.spring.jwt.serviceStation.entity.ServiceStation;
import com.spring.jwt.serviceStation.entity.ServiceStationBooking;
import com.spring.jwt.serviceStation.mapper.BookingMapper;
import com.spring.jwt.serviceStation.repository.ServiceStationBookingRepository;
import com.spring.jwt.serviceStation.repository.ServiceStationRepository;
import com.spring.jwt.serviceStation.service.ServiceStationBookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ServiceStationBookingServiceImpl
        implements ServiceStationBookingService {

    private final ServiceStationBookingRepository bookingRepository;
    private final ServiceStationRepository serviceStationRepository;

    @Override
    public BookingResponseDto createBooking(Long stationId, BookingCreateDto dto) {

        ServiceStation station = serviceStationRepository.findById(Math.toIntExact(stationId))
                .orElseThrow(() -> new RuntimeException("Service station not found"));

        ServiceStationBooking booking = new ServiceStationBooking();
        booking.setServiceStation(station);
        booking.setTechnicianName(dto.getTechnicianName());
        booking.setServiceCharges(dto.getServiceCharges());
        booking.setServiceNotes(dto.getServiceNotes());
        booking.setServiceStatus(dto.getServiceStatus());

        ServiceStationBooking saved = bookingRepository.save(booking);
        return BookingMapper.toResponseDto(saved);
    }

    @Override
    public List<BookingResponseDto> getAllBookings(Long stationId) {

        serviceStationRepository.findById(Math.toIntExact(stationId))
                .orElseThrow(() -> new RuntimeException("Service station not found"));

        List<ServiceStationBooking> bookings =
                bookingRepository.findByServiceStation_Id(stationId);

        List<BookingResponseDto> responseList =
                new ArrayList<BookingResponseDto>();

        for (ServiceStationBooking booking : bookings) {
            responseList.add(BookingMapper.toResponseDto(booking));
        }

        return responseList;
    }

    @Override
    public BookingResponseDto getBooking(Long stationId, Long bookingId) {

        ServiceStationBooking booking = bookingRepository
                .findByIdAndServiceStation_Id(bookingId, stationId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found for this station"));

        return BookingMapper.toResponseDto(booking);
    }

    @Override
    public BookingResponseDto updateStatus(
            Long stationId,
            Long bookingId,
            String status
    ) {

        ServiceStationBooking booking = bookingRepository
                .findByIdAndServiceStation_Id(bookingId, stationId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Normalize status
        status = status.toUpperCase();

        // Validate transition
        validateStatusFlow(booking.getServiceStatus(), status);

        booking.setServiceStatus(status);

        //  Set start time only once
        if ("IN_PROGRESS".equals(status) && booking.getServiceStartTime() == null) {
            booking.setServiceStartTime(LocalDateTime.now());
        }

        //  Set end time only once
        if ("COMPLETED".equals(status) && booking.getServiceEndTime() == null) {
            booking.setServiceEndTime(LocalDateTime.now());
        }

        ServiceStationBooking saved = bookingRepository.save(booking);
        return BookingMapper.toResponseDto(saved);
    }

    @Override
    public BookingResponseDto updateCharges(
            Long stationId,
            Long bookingId,
            BigDecimal amount
    ) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid service charges");
        }

        ServiceStationBooking booking = bookingRepository
                .findByIdAndServiceStation_Id(bookingId, stationId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setServiceCharges(amount);

        ServiceStationBooking saved = bookingRepository.save(booking);
        return BookingMapper.toResponseDto(saved);
    }

    @Override
    public BookingResponseDto completeService(
            Long stationId,
            Long bookingId,
            String notes
    ) {

        ServiceStationBooking booking = bookingRepository
                .findByIdAndServiceStation_Id(bookingId, stationId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        booking.setServiceStatus("COMPLETED");
        booking.setServiceNotes(notes);
        booking.setServiceEndTime(LocalDateTime.now());

        ServiceStationBooking saved = bookingRepository.save(booking);
        return BookingMapper.toResponseDto(saved);
    }

    @Override
    public void deleteBooking(Long stationId, Long bookingId) {
        ServiceStationBooking booking = bookingRepository
                .findByIdAndServiceStation_Id(bookingId, stationId)
                .orElseThrow(() ->
                        new RuntimeException("Booking not found for this station"));

        bookingRepository.delete(booking);
    }

    // Java 7–style status validation
    private void validateStatusFlow(String current, String next) {

        if (current == null) {
            current = "PENDING";
        }

        if ("PENDING".equalsIgnoreCase(current)
                && !"IN_PROGRESS".equalsIgnoreCase(next)) {
            throw new RuntimeException(
                    "Only IN_PROGRESS allowed from PENDING");
        }

        if ("IN_PROGRESS".equalsIgnoreCase(current)
                && !"COMPLETED".equalsIgnoreCase(next)) {
            throw new RuntimeException(
                    "Only COMPLETED allowed from IN_PROGRESS");
        }
    }
}
