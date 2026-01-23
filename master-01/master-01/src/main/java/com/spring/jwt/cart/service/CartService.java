package com.spring.jwt.cart.service;

import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.dto.CartUpdateDTO;

import java.util.List;

public interface CartService {

    CartResponseDTO addToCart(Integer userId, CartRequestDTO dto);

    CartResponseDTO updateCart(CartUpdateDTO dto);

    void removeFromCart(Integer userId, Long premiumCarId);

    List<CartResponseDTO> getUserCart(Integer userId);
}
