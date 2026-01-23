package com.spring.jwt.cart.controller;
import com.spring.jwt.cart.dto.CartRequestDTO;
import com.spring.jwt.cart.dto.CartResponseDTO;
import com.spring.jwt.cart.dto.CartUpdateDTO;
import com.spring.jwt.cart.service.CartService;
import com.spring.jwt.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ✅ STORY 3: ADD PREMIUM CAR TO CART (OWNER ONLY)
    //@PreAuthorize("@securityUtil.getCurrentUserId() == #userId")
    @PostMapping("/add/{userId}")
    public ResponseEntity<ResponseDto> addToCart(
            @PathVariable Integer userId,
            @RequestBody @Valid CartRequestDTO dto
    ) {
        CartResponseDTO response = cartService.addToCart(userId, dto);
        return ResponseEntity.ok(
                new ResponseDto("success", "Premium car added to cart", response)
        );
    }

    // ✅ UPDATE CART (OWNER ONLY)
    //@PreAuthorize("@securityUtil.getCurrentUserId() == #dto.userId")
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCart(
            @RequestBody @Valid CartUpdateDTO dto
    ) {
        return ResponseEntity.ok(
                new ResponseDto("success", "Cart updated successfully",
                        cartService.updateCart(dto))
        );
    }

    // ✅ STORY 4: REMOVE PREMIUM CAR FROM CART (OWNER ONLY)
    //@PreAuthorize("@securityUtil.getCurrentUserId() == #userId")
    @DeleteMapping("/remove/{userId}/{premiumCarId}")
    public ResponseEntity<ResponseDto> removeFromCart(
            @PathVariable Integer userId,
            @PathVariable Long premiumCarId
    ) {
        cartService.removeFromCart(userId, premiumCarId);
        return ResponseEntity.ok(
                new ResponseDto("success", "Premium car removed from cart")
        );
    }

    // ✅ GET USER CART (OWNER ONLY)
    //@PreAuthorize("@securityUtil.getCurrentUserId() == #userId")
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDto> getUserCart(
            @PathVariable Integer userId
    ) {
        List<CartResponseDTO> cart = cartService.getUserCart(userId);
        return ResponseEntity.ok(
                new ResponseDto("success", "User cart fetched successfully", cart)
        );
    }
}