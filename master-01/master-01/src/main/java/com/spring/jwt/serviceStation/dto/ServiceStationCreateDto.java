package com.spring.jwt.serviceStation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStationCreateDto {

    @NotBlank
    private String stationName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String mobileNo;

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
    // 🔥 IMPORTANT
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
