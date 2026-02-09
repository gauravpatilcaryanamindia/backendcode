package com.spring.jwt.serviceStation.repository;
import com.spring.jwt.serviceStation.entity.ServiceStationBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceStationBookingRepository
        extends JpaRepository<ServiceStationBooking, Long> {

    List<ServiceStationBooking> findByServiceStation_Id(Long stationId);

    Optional<ServiceStationBooking> findByIdAndServiceStation_Id(
            Long bookingId,
            Long stationId
    );
}