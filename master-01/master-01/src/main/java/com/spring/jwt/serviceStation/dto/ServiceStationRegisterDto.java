package com.spring.jwt.serviceStation.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ServiceStationRegisterDto {

    @NotBlank
    private String name;

    @Email
    private String email;

    @Size(min = 6)
    private String password;
}


