package com.spring.jwt.serviceStation.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceStationDocumentResponseDto {

    private Long id;
    private String documentType;
    private String fileName;
    private Boolean verified;
    private LocalDateTime uploadedAt;
}
