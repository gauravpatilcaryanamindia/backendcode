package com.spring.jwt.cart.dto;

import lombok.*;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDTO {

    private Long cartId;

    private Long userId;

    private Long premiumCarId;

    private String premiumCarName;

    private Double price;

    private Long quantity;

    private Double totalPrice;

    private LocalDateTime createdAt;

    private String status;

    private String message;
}