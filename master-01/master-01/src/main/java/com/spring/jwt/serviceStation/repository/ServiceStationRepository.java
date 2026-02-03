package com.spring.jwt.serviceStation.repository;

import com.spring.jwt.serviceStation.entity.ServiceStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceStationRepository extends JpaRepository<ServiceStation, Long> {}
