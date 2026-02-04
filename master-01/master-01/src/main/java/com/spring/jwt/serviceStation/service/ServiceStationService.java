package com.spring.jwt.serviceStation.service;

import com.spring.jwt.serviceStation.dto.ServiceStationCreateDto;
import com.spring.jwt.serviceStation.dto.ServiceStationDocumentResponseDto;
import com.spring.jwt.serviceStation.dto.ServiceStationResponseDto;
import com.spring.jwt.serviceStation.dto.ServiceStationUpdateDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface ServiceStationService {

    ServiceStationResponseDto create(ServiceStationCreateDto dto);

    ServiceStationResponseDto getById(Integer id);

    List<ServiceStationResponseDto> getAll();

    ServiceStationResponseDto update(Integer id, ServiceStationUpdateDto dto);

    void delete(Integer id);

    ServiceStationResponseDto getMyProfile();

    ServiceStationResponseDto updateMyProfile(ServiceStationUpdateDto dto);

    ServiceStationResponseDto uploadDocument(String documentType, MultipartFile file) throws IOException;

    List<ServiceStationDocumentResponseDto> getMyDocuments();

    byte[] downloadDocument(Long documentId);

    ServiceStationDocumentResponseDto verifyDocument(Long documentId);
}
