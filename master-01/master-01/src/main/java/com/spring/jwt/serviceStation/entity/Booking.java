package com.spring.jwt.serviceStation.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Booking {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private ServiceStation serviceStation;

    private String customerName;

    private String status; // pending, in-progress, completed, rejected

    private String notes;
    private Double charges;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
