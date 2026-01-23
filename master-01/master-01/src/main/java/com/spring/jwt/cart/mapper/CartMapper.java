package com.spring.jwt.cart.mapper;

import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.entity.CartEntity;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumCar.PremiumCar;

import java.time.LocalDateTime;

public class CartMapper {

    private CartMapper() {
        // utility class
    }

    // DTO → Entity

    // Map CartRequestDTO -> CartEntity
    public static CartEntity toEntity(CartRequestDTO dto, User user, PremiumCar premiumCar) {
        CartEntity entity = new CartEntity();
        entity.setUser(user);
        entity.setPremiumCar(premiumCar);
        entity.setQuantity(dto.getQuantity());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    // Entity → DTO
    public static CartResponseDTO toResponseDTO(CartEntity entity) {

        PremiumCar car = entity.getPremiumCar();

        return CartResponseDTO.builder()
                .cartId(entity.getId())
                .userId(Long.valueOf(entity.getUser().getId()))
                .premiumCarId(car.getPremiumCarId().longValue())
                .premiumCarName(car.getBrand() + " " + car.getModel())
                .price(car.getPrice().doubleValue())
                .quantity(entity.getQuantity())
                .totalPrice((double) (car.getPrice() * entity.getQuantity()))
                .createdAt(entity.getCreatedAt())
                .build();
    }


}