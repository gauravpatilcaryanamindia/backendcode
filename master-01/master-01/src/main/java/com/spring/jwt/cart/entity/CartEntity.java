package com.spring.jwt.cart.entity;


import com.spring.jwt.entity.User;
import com.spring.jwt.premiumCar.PremiumCar;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "cart",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "premium_car_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cart belongs to one user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Cart contains one premium car
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "premium_car_id", nullable = false)
    private PremiumCar premiumCar;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean active;

    // Automatically set timestamps
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
