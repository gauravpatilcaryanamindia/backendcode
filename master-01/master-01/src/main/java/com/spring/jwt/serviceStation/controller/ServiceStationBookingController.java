package com.spring.jwt.serviceStation.controller;

import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingCreateDto;
import com.spring.jwt.serviceStation.dto.ServiceStationbookingDto.BookingResponseDto;
import com.spring.jwt.serviceStation.service.ServiceStationBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/service-stations/{stationId}")
@RequiredArgsConstructor
public class ServiceStationBookingController {

    private final ServiceStationBookingService bookingService;

    // ✅ Create booking
    @PostMapping("/bookings")
    public ResponseEntity<BookingResponseDto> createBooking(
            @PathVariable("stationId") Long stationId,
            @RequestBody BookingCreateDto dto
    ) {
        BookingResponseDto response =
                bookingService.createBooking(stationId, dto);

        return new ResponseEntity<BookingResponseDto>(
                response, HttpStatus.CREATED);
    }

    // ✅ Get all bookings of station
    @GetMapping("/getAll")
   public ResponseEntity<List<BookingResponseDto>> getAllBookings(
            @PathVariable("stationId") Long stationId
    ) {
        List<BookingResponseDto> responseList =
                bookingService.getAllBookings(stationId);

        return new ResponseEntity<List<BookingResponseDto>>(
                responseList, HttpStatus.OK);
    }

    // ✅ Get single booking
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBooking(
            @PathVariable("stationId") Long stationId,
            @PathVariable("bookingId") Long bookingId
    ) {
        BookingResponseDto response =
                bookingService.getBooking(stationId, bookingId);

        return new ResponseEntity<BookingResponseDto>(
                response, HttpStatus.OK);
    }

    // ✅ Update booking status
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingResponseDto> updateStatus(
            @PathVariable("stationId") Long stationId,
            @PathVariable("bookingId") Long bookingId,
            @RequestParam("status") String status
    ) {
        BookingResponseDto response =
                bookingService.updateStatus(stationId, bookingId, status);

        return new ResponseEntity<BookingResponseDto>(
                response, HttpStatus.OK);
    }

    // ✅ Update service charges
    @PutMapping("/{bookingId}/charges")
    public ResponseEntity<BookingResponseDto> updateCharges(
            @PathVariable("stationId") Long stationId,
            @PathVariable("bookingId") Long bookingId,
            @RequestParam("amount") BigDecimal amount
    ) {
        BookingResponseDto response =
                bookingService.updateCharges(stationId, bookingId, amount);

        return new ResponseEntity<BookingResponseDto>(
                response, HttpStatus.OK);
    }

    // ✅ Complete service
    @PutMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponseDto> completeService(
            @PathVariable("stationId") Long stationId,
            @PathVariable("bookingId") Long bookingId,
            @RequestParam(value = "notes", required = false) String notes
    ) {
        BookingResponseDto response =
                bookingService.completeService(stationId, bookingId, notes);

        return new ResponseEntity<BookingResponseDto>(
                response, HttpStatus.OK);
    }
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> deleteBooking(
            @PathVariable("stationId") Long stationId,
            @PathVariable("bookingId") Long bookingId
    ) {
        bookingService.deleteBooking(stationId, bookingId);

        return new ResponseEntity<String>(
                "Booking deleted successfully",
                HttpStatus.OK
        );
    }
}
