package com.spring.jwt.serviceStation.entity;

import com.spring.jwt.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_station")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_station_id")
    private Integer id;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "owner_first_name")
    private String firstName;

    @Column(name = "owner_last_name")
    private String lastName;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "area")
    private String area;

    @Column(name = "active_status")
    private Boolean status = true;

    @Column(name = "service_types")
    private String serviceTypes;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "profile_completed")
    private Boolean profileCompleted = false;

    @Column(name = "gst_doc_id")
    private Long gstDocId;

    @Column(name = "pan_doc_id")
    private Long panDocId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
