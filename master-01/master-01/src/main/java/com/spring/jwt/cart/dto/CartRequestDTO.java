package com.spring.jwt.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartRequestDTO {

    @NotNull(message = "Premium car id is required")
    private Long premiumCarId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Long quantity;
}