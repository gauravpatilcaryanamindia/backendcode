package com.spring.jwt.serviceStation.repository;
import com.spring.jwt.serviceStation.entity.ServiceStationBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ServiceStationBookingRepository
        extends JpaRepository<ServiceStationBooking, Long> {

    List<ServiceStationBooking> findByServiceStation_Id(Long stationId);

    Optional<ServiceStationBooking> findByIdAndServiceStation_Id(
            Long bookingId,
            Long stationId
    );

    long countByServiceStation_Id(Long stationId);

    long countByServiceStation_IdAndServiceStatus(
            Long stationId,
            String serviceStatus
    );

    @Query("""
        SELECT COUNT(b)
        FROM ServiceStationBooking b
        WHERE b.serviceStation.id = :stationId
        AND b.createdAt >= :start
        AND b.createdAt < :end
    """)
    long countTodayBookings(
            Long stationId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
        SELECT COALESCE(SUM(b.serviceCharges), 0)
        FROM ServiceStationBooking b
        WHERE b.serviceStation.id = :stationId
        AND b.serviceStatus = 'COMPLETED'
    """)
    BigDecimal calculateTotalRevenue(Long stationId);
}