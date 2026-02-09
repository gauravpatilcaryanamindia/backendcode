package com.spring.jwt.serviceStation.entity;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_station_booking")
@Getter
@Setter
public class ServiceStationBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_station_id", nullable = false)
    private ServiceStation serviceStation;


    @Column(nullable = false)
    private String serviceStatus;   // PENDING / IN_PROGRESS / COMPLETED / REJECTED

    private BigDecimal serviceCharges;

    @Column(length = 1000)
    private String serviceNotes;

    private String technicianName;


    private LocalDateTime serviceStartTime;
    private LocalDateTime serviceEndTime;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();

        // ✅ only default if NOT provided
        if (this.serviceStatus == null) {
            this.serviceStatus = "PENDING";
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


