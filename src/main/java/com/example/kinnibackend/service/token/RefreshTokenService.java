package com.example.kinnibackend.service.token;

import com.example.kinnibackend.entity.RefreshToken;
import com.example.kinnibackend.repository.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByUserId(Long userId){
        return refreshTokenRepository.findByUserId(userId);
    }

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public void save(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

}
