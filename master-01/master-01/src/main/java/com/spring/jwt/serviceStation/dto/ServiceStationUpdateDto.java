package com.spring.jwt.serviceStation.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStationUpdateDto {

    @NotBlank
    private String stationName;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String area;

    private String serviceTypes;

    private String bankName;

    private String accountNumber;

    private String ifscCode;
}

