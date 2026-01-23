package com.spring.jwt.cart.repository;

import com.spring.jwt.cart.entity.CartEntity;
import com.spring.jwt.entity.User;
import com.spring.jwt.premiumCar.PremiumCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserAndPremiumCar(User user, PremiumCar premiumCar);

    List<CartEntity> findByUser(User user);

    void deleteByUserAndPremiumCar(User user, PremiumCar premiumCar);
}
