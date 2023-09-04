package com.example.kinnibackend.repository.token;


import com.example.kinnibackend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);
    RefreshToken findByRefreshToken(String refreshToken);
}

