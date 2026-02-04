package com.spring.jwt.serviceStation.controller;

import com.spring.jwt.common.MessageConfig;
import com.spring.jwt.serviceStation.dto.*;
import com.spring.jwt.serviceStation.service.ServiceStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/service-station")
@RequiredArgsConstructor
public class ServiceStationController {

    private final ServiceStationService service;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@RequestBody ServiceStationCreateDto dto) {
        // -------- VALIDATIONS --------
        if (!StringUtils.hasText(dto.getStationName())) {
            return ResponseHandler.generateResponse("Station name is required", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getFirstName())) {
            return ResponseHandler.generateResponse("First name is required", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getLastName())) {
            return ResponseHandler.generateResponse("Last name is required", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getEmail())) {
            return ResponseHandler.generateResponse(MessageConfig.EMAIL_REQUIRED, HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getPassword()) || dto.getPassword().length() < 6) {
            return ResponseHandler.generateResponse("Password must be at least 6 characters", HttpStatus.BAD_REQUEST, null);
        }
        if (!dto.getMobileNo().matches("^[0-9]{10}$")) {
            return ResponseHandler.generateResponse("Invalid mobile number", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getBankName()) || !StringUtils.hasText(dto.getAccountNumber()) || !StringUtils.hasText(dto.getIfscCode())) {
            return ResponseHandler.generateResponse(MessageConfig.BANK_REQUIRED, HttpStatus.BAD_REQUEST, null);
        }
        // -------- SERVICE CALL --------
        ServiceStationResponseDto response = service.create(dto);
        return ResponseHandler.generateResponse(MessageConfig.CREATED, HttpStatus.CREATED, response);
    }

    // GET BY ID
    @GetMapping("getById/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseHandler.generateResponse("Invalid service station id", HttpStatus.BAD_REQUEST, null);
        }
        ServiceStationResponseDto response = service.getById(id);
        if (response == null) {
            return ResponseHandler.generateResponse(MessageConfig.NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("Service station found", HttpStatus.OK, response);
    }

    // GET ALL
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDto> getAll() {
        List<ServiceStationResponseDto> list = service.getAll();
        return ResponseHandler.generateResponse("Service station list", HttpStatus.OK, list);
    }

    // UPDATE BY ID
    @PutMapping("updateStation/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable Integer id, @RequestBody ServiceStationUpdateDto dto) {
        if (id == null || id <= 0) {
            return ResponseHandler.generateResponse("Invalid service station id", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getStationName())) {
            return ResponseHandler.generateResponse("Station name is required", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getCity()) || !StringUtils.hasText(dto.getArea())) {
            return ResponseHandler.generateResponse("City and area are required", HttpStatus.BAD_REQUEST, null);
        }
        ServiceStationResponseDto response = service.update(id, dto);
        if (response == null) {
            return ResponseHandler.generateResponse(MessageConfig.NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse(MessageConfig.UPDATED, HttpStatus.OK, response);
    }

    // DELETE
    @DeleteMapping("deleteStation/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseHandler.generateResponse("Invalid service station id", HttpStatus.BAD_REQUEST, null);
        }
        service.delete(id);
        return ResponseHandler.generateResponse(MessageConfig.DELETED, HttpStatus.OK, null);
    }
    // ================= SERVICE STATION APIs =================

    // MY PROFILE
    @GetMapping("/getMyProfile")
    public ResponseEntity<ResponseDto> myProfile() {
        ServiceStationResponseDto response = service.getMyProfile();
        if (response == null) {
            return ResponseHandler.generateResponse(MessageConfig.NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("My profile", HttpStatus.OK, response);
    }

    // UPDATE MY PROFILE
    @PutMapping("/UpdateMyProfile")
    public ResponseEntity<ResponseDto> updateMyProfile(
            @RequestBody ServiceStationUpdateDto dto) {
        if (!StringUtils.hasText(dto.getStationName())) {
            return ResponseHandler.generateResponse("Station name is required", HttpStatus.BAD_REQUEST, null);
        }
        if (!StringUtils.hasText(dto.getBankName()) || !StringUtils.hasText(dto.getAccountNumber()) || !StringUtils.hasText(dto.getIfscCode())) {
            return ResponseHandler.generateResponse(MessageConfig.BANK_REQUIRED, HttpStatus.BAD_REQUEST, null);
        }
        ServiceStationResponseDto response =
                service.updateMyProfile(dto);
        if (response == null) {
            return ResponseHandler.generateResponse(MessageConfig.NOT_FOUND, HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse(MessageConfig.UPDATED, HttpStatus.OK, response);
    }

    @PostMapping("/documents/upload")
    public ResponseEntity<ResponseDto> uploadDocument(@RequestParam String documentType, @RequestParam MultipartFile file) throws IOException {
        if (!StringUtils.hasText(documentType)) {
            return ResponseHandler.generateResponse("Document type required", HttpStatus.BAD_REQUEST, null);
        }
        if (file == null || file.isEmpty()) {
            return ResponseHandler.generateResponse("File required", HttpStatus.BAD_REQUEST, null);
        }
        ServiceStationResponseDto response = service.uploadDocument(documentType, file);
        return ResponseHandler.generateResponse("Document uploaded", HttpStatus.OK, response);
    }

    @GetMapping("/documents/me")
    public ResponseEntity<ResponseDto> myDocuments() {
        return ResponseHandler.generateResponse(
                "My documents", HttpStatus.OK, service.getMyDocuments());
    }

    @GetMapping("/documents/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        byte[] file = service.downloadDocument(id);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=document").body(file);
    }

    @PutMapping("/documents/{id}/verify")
    public ResponseEntity<ResponseDto> verify(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                "Document verified", HttpStatus.OK, service.verifyDocument(id));
    }

}
