package com.spring.jwt.cart.serviceImpl;

import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.dto.CartUpdateDTO;
import com.spring.jwt.cart.entity.CartEntity;
import com.spring.jwt.cart.mapper.CartMapper;
import com.spring.jwt.cart.repository.CartRepository;
import com.spring.jwt.cart.service.CartService;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumCar.PremiumCar;
import com.spring.jwt.premiumCar.PremiumCarRepository;
import com.spring.jwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final PremiumCarRepository premiumCarRepository;

    // ✅ ADD TO CART
    @Override
    public CartResponseDTO addToCart(Integer userId, CartRequestDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PremiumCar premiumCar = premiumCarRepository.findById(dto.getPremiumCarId().intValue())
                .orElseThrow(() -> new RuntimeException("Premium car not found"));

        CartEntity cartEntity = cartRepository
                .findByUserAndPremiumCar(user, premiumCar)
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + dto.getQuantity());
                    return existing;
                })
                .orElseGet(() ->
                        CartMapper.toEntity(dto, user, premiumCar)
                );

        CartEntity saved = cartRepository.save(cartEntity);
        return CartMapper.toResponseDTO(saved);
    }

    // ✅ UPDATE CART
    @Override
    public CartResponseDTO updateCart(CartUpdateDTO dto) {

        CartEntity cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setQuantity(dto.getQuantity());

        return CartMapper.toResponseDTO(cartRepository.save(cart));
    }

    // ✅ REMOVE FROM CART
    @Override
    public void removeFromCart(Integer userId, Long premiumCarId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PremiumCar premiumCar = premiumCarRepository.findById(premiumCarId.intValue())
                .orElseThrow(() -> new RuntimeException("Premium car not found"));

        cartRepository.deleteByUserAndPremiumCar(user, premiumCar);
    }

    // ✅ GET USER CART
    @Override
    public List<CartResponseDTO> getUserCart(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
                .stream()
                .map(CartMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}