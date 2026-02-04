package com.spring.jwt.serviceStation.repository;

import com.spring.jwt.serviceStation.entity.ServiceStationDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceStationDocumentRepository
        extends JpaRepository<ServiceStationDocument, Long> {

    List<ServiceStationDocument> findByServiceStationId(Integer stationId);

}
