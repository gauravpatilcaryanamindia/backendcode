package com.spring.jwt.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartUpdateDTO {

    @NotNull(message = "Cart id is required")
    private Long cartId;

    @Min(value = 1, message = "Quantity must be greater than zero")
    private Long quantity;
}
