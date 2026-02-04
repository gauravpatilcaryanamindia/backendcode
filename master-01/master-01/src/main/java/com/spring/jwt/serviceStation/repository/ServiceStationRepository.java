package com.spring.jwt.serviceStation.repository;

import com.spring.jwt.serviceStation.entity.ServiceStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceStationRepository
        extends JpaRepository<ServiceStation, Integer> {

    Optional<ServiceStation> findByUserId(Integer userId);

    List<ServiceStation> findByStatusTrue();
}
