package com.spring.jwt.serviceStation.dto;

import lombok.Data;

@Data
public class ServiceStationResponseDto {

    private Integer id;
    private String stationName;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String city;
    private Boolean status;
    private String email;
    private String area;
    private String serviceTypes;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private Boolean profileCompleted;
    private Long gstDocId;
    private Long panDocId;
}
