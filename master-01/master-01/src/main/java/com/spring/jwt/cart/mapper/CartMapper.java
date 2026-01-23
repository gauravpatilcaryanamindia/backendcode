package com.spring.jwt.cart.mapper;

import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.entity.CartEntity;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumCar.PremiumCar;

public class CartMapper {

    private CartMapper() {
        // utility class
    }

    // DTO → Entity
    public static CartEntity toEntity(
            CartRequestDTO dto,
            User user,
            PremiumCar premiumCar
    ) {
        return CartEntity.builder()
                .user(user)
                .premiumCar(premiumCar)
                .quantity(dto.getQuantity())
                .active(true)
                .build();
    }

    // Entity → DTO
    public static CartResponseDTO toResponseDTO(CartEntity entity) {

        PremiumCar car = entity.getPremiumCar();

        return CartResponseDTO.builder()
                .cartId(entity.getId())
                .userId(Long.valueOf(entity.getUser().getId()))
                .premiumCarId(car.getPremiumCarId().longValue()) // ✅ FIX
                .premiumCarName(
                        car.getBrand() + " " + car.getModel()     // ✅ FIX
                )
                .price(car.getPrice().doubleValue())             // ✅ FIX
                .quantity(entity.getQuantity())
                .totalPrice(
                        (double) (car.getPrice() * entity.getQuantity())
                )
                .createdAt(entity.getCreatedAt())
                .build();
    }
}