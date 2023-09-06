package com.example.kinnibackend.service.token;


import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.token.RefreshTokenRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenRepository.findByRefreshToken(refreshToken).getUserId();

        Users user = userRepository.findByUserId(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}

