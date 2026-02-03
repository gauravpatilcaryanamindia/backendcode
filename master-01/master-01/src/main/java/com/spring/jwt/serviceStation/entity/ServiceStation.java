package com.spring.jwt.serviceStation.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStation {

    @Id @GeneratedValue
    private Long id;

    private String stationName;
    private String email;
    private String phone;

    private String address;
    private String bankDetails;
    private String serviceTypes;

    private Boolean active = true;
}

