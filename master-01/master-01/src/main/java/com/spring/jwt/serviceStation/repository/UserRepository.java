package com.spring.jwt.serviceStation.repository;
import com.spring.jwt.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
