package com.spring.jwt.cart.controller;

import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.dto.CartUpdateDTO;
import com.spring.jwt.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ✅ ADD PREMIUM CAR TO CART
    @PostMapping("/add/{userId}")
    public ResponseEntity<CartResponseDTO> addToCart(
            @PathVariable Integer userId,
            @RequestBody @Valid CartRequestDTO dto
    ) {
        return ResponseEntity.ok(cartService.addToCart(userId, dto));
    }

    // ✅ UPDATE CART
    @PutMapping("/update")
    public ResponseEntity<CartResponseDTO> updateCart(
            @RequestBody @Valid CartUpdateDTO dto
    ) {
        return ResponseEntity.ok(cartService.updateCart(dto));
    }

    // ✅ REMOVE FROM CART
    @DeleteMapping("/remove/{userId}/{premiumCarId}")
    public ResponseEntity<String> removeFromCart(
            @PathVariable Integer userId,
            @PathVariable Long premiumCarId
    ) {
        cartService.removeFromCart(userId, premiumCarId);
        return ResponseEntity.ok("Item removed from cart");
    }

    // ✅ GET USER CART
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getUserCart(
            @PathVariable Integer userId
    ) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }
}
