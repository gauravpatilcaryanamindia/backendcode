package com.spring.jwt.serviceStation.service.impl;

import com.spring.jwt.entity.Role;
import com.spring.jwt.entity.User;
import com.spring.jwt.repository.RoleRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.serviceStation.dto.ServiceStationCreateDto;
import com.spring.jwt.serviceStation.dto.ServiceStationDocumentResponseDto;
import com.spring.jwt.serviceStation.dto.ServiceStationResponseDto;
import com.spring.jwt.serviceStation.dto.ServiceStationUpdateDto;
import com.spring.jwt.serviceStation.entity.ServiceStation;
import com.spring.jwt.serviceStation.entity.ServiceStationDocument;
import com.spring.jwt.serviceStation.repository.ServiceStationDocumentRepository;
import com.spring.jwt.serviceStation.repository.ServiceStationRepository;
import com.spring.jwt.serviceStation.service.ServiceStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ServiceStationServiceImpl implements ServiceStationService {

    private final ServiceStationRepository stationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private ServiceStationDocumentRepository documentRepository;

    @Override
    public ServiceStationResponseDto create(ServiceStationCreateDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())
                || userRepository.existsByMobileNo(dto.getMobileNo())) {
            return null;
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setMobileNo(dto.getMobileNo());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        Role role = roleRepository.findByName("SERVICE_STATION");
        user.setRoles(Set.of(role));
        userRepository.save(user);
        ServiceStation station = new ServiceStation();
        station.setStationName(dto.getStationName());
        station.setFirstName(dto.getFirstName());
        station.setLastName(dto.getLastName());
        station.setMobileNo(dto.getMobileNo());
        station.setAddress(dto.getAddress());
        station.setCity(dto.getCity());
        station.setArea(dto.getArea());
        station.setServiceTypes(dto.getServiceTypes());
        station.setBankName(dto.getBankName());
        station.setAccountNumber(dto.getAccountNumber());
        station.setIfscCode(dto.getIfscCode());
        station.setStatus(true);
        station.setUser(user);
        updateProfileStatus(station);
        stationRepository.save(station);
        return mapToDto(station);
    }

    @Override
    public ServiceStationResponseDto getById(Integer id) {
        Optional<ServiceStation> optional = stationRepository.findById(id);
        if (optional.isEmpty() || !optional.get().getStatus()) {
            return null;
        }
        return mapToDto(optional.get());
    }

    @Override
    public List<ServiceStationResponseDto> getAll() {
        List<ServiceStation> list = stationRepository.findByStatusTrue();
        List<ServiceStationResponseDto> response = new ArrayList<>();
        for (ServiceStation station : list) {
            response.add(mapToDto(station));
        }
        return response;
    }

    @Override
    public ServiceStationResponseDto update(Integer id, ServiceStationUpdateDto dto) {
        Optional<ServiceStation> optional = stationRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        ServiceStation station = optional.get();
        station.setStationName(dto.getStationName());
        station.setAddress(dto.getAddress());
        station.setCity(dto.getCity());
        station.setArea(dto.getArea());
        station.setServiceTypes(dto.getServiceTypes());
        station.setBankName(dto.getBankName());
        station.setAccountNumber(dto.getAccountNumber());
        station.setIfscCode(dto.getIfscCode());
        updateProfileStatus(station);
        stationRepository.save(station);
        return mapToDto(station);
    }

    @Override
    public void delete(Integer id) {
        Optional<ServiceStation> optional =
                stationRepository.findById(id);
        if (optional.isPresent()) {
            ServiceStation station = optional.get();
            station.setStatus(false);
            stationRepository.save(station);
        }
    }

    @Override
    public ServiceStationResponseDto getMyProfile() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        User user =
                userRepository.findByEmail(auth.getName());
        Optional<ServiceStation> optional =
                stationRepository.findByUserId(user.getId());
        if (optional.isEmpty()) {
            return null;
        }
        return mapToDto(optional.get());
    }

    @Override
    public ServiceStationResponseDto updateMyProfile(ServiceStationUpdateDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Optional<ServiceStation> optional = stationRepository.findByUserId(user.getId());
        if (optional.isEmpty()) {
            return null;
        }
        ServiceStation station = optional.get();
        station.setStationName(dto.getStationName());
        station.setAddress(dto.getAddress());
        station.setCity(dto.getCity());
        station.setArea(dto.getArea());
        station.setServiceTypes(dto.getServiceTypes());
        station.setBankName(dto.getBankName());
        station.setAccountNumber(dto.getAccountNumber());
        station.setIfscCode(dto.getIfscCode());
        updateProfileStatus(station);
        stationRepository.save(station);
        return mapToDto(station);
    }

    private void updateProfileStatus(ServiceStation station) {
        boolean completed =
                StringUtils.hasText(station.getBankName())
                        && StringUtils.hasText(station.getAccountNumber())
                        && StringUtils.hasText(station.getIfscCode())
                        && StringUtils.hasText(station.getServiceTypes())
                        && station.getGstDocId() != null
                        && station.getPanDocId() != null;

        station.setProfileCompleted(completed);
    }

    @Override
    public ServiceStationResponseDto uploadDocument(String documentType, MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        ServiceStation station = stationRepository.findByUserId(user.getId()).orElseThrow();
        if ("GST".equalsIgnoreCase(documentType)
                && station.getGstDocId() != null) {
            throw new RuntimeException("GST document already uploaded");
        }
        if ("PAN".equalsIgnoreCase(documentType)
                && station.getPanDocId() != null) {
            throw new RuntimeException("PAN document already uploaded");
        }
        String dir = "uploads/service-station/";
        new File(dir).mkdirs();
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(dir + fileName);
        Files.copy(file.getInputStream(), path);
        ServiceStationDocument doc = new ServiceStationDocument();
        doc.setDocumentType(documentType);
        doc.setFileName(file.getOriginalFilename());
        doc.setFilePath(path.toString());
        doc.setServiceStation(station);
        documentRepository.save(doc);
        if ("GST".equalsIgnoreCase(documentType)) {
            station.setGstDocId(doc.getId());
        }
        if ("PAN".equalsIgnoreCase(documentType)) {
            station.setPanDocId(doc.getId());
        }
        updateProfileStatus(station);
        stationRepository.save(station);
        return mapToDto(station);
    }

    @Override
    public List<ServiceStationDocumentResponseDto> getMyDocuments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        ServiceStation station = stationRepository.findByUserId(user.getId()).orElseThrow();
        List<ServiceStationDocument> docs = documentRepository.findByServiceStationId(station.getId());
        List<ServiceStationDocumentResponseDto> response = new ArrayList<>();
        for (ServiceStationDocument doc : docs) {
            ServiceStationDocumentResponseDto dto = new ServiceStationDocumentResponseDto();
            dto.setId(doc.getId());
            dto.setDocumentType(doc.getDocumentType());
            dto.setFileName(doc.getFileName());
            dto.setVerified(doc.getVerified());
            dto.setUploadedAt(doc.getUploadedAt());
            response.add(dto);
        }
        return response;
    }

    @Override
    public byte[] downloadDocument(Long documentId) {
        ServiceStationDocument doc = documentRepository.findById(documentId).orElseThrow();
        try {
            return Files.readAllBytes(Paths.get(doc.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    @Override
    public ServiceStationDocumentResponseDto verifyDocument(Long documentId) {
        ServiceStationDocument doc = documentRepository.findById(documentId).orElseThrow();
        doc.setVerified(true);
        documentRepository.save(doc);
        ServiceStationDocumentResponseDto dto = new ServiceStationDocumentResponseDto();
        dto.setId(doc.getId());
        dto.setDocumentType(doc.getDocumentType());
        dto.setFileName(doc.getFileName());
        dto.setVerified(doc.getVerified());
        dto.setUploadedAt(doc.getUploadedAt());
        return dto;
    }


    private ServiceStationResponseDto mapToDto(ServiceStation station) {
        ServiceStationResponseDto dto = new ServiceStationResponseDto();
        dto.setId(station.getId());
        dto.setStationName(station.getStationName());
        dto.setFirstName(station.getFirstName());
        dto.setLastName(station.getLastName());
        dto.setMobileNo(station.getMobileNo());
        dto.setCity(station.getCity());
        dto.setArea(station.getArea());
        dto.setStatus(station.getStatus());
        dto.setEmail(station.getUser().getEmail());
        dto.setServiceTypes(station.getServiceTypes());
        dto.setBankName(station.getBankName());
        dto.setAccountNumber(station.getAccountNumber());
        dto.setIfscCode(station.getIfscCode());
        dto.setProfileCompleted(station.getProfileCompleted());
        return dto;
    }
}
