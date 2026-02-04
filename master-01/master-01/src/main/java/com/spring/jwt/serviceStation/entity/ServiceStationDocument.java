package com.spring.jwt.serviceStation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_station_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStationDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType;
    private String fileName;
    private String filePath;

    private Boolean verified = false;

    @ManyToOne
    @JoinColumn(name = "service_station_id")
    private ServiceStation serviceStation;

    private LocalDateTime uploadedAt;

    @PrePersist
    public void onCreate() {
        this.uploadedAt = LocalDateTime.now();
    }
}

