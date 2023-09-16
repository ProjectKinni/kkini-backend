//package com.example.kinnibackend.token;
//
//import com.example.kinnibackend.entity.RefreshToken;
//import com.example.kinnibackend.service.token.RefreshTokenService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest
//public class RefreshTokenServiceTest {
//
//    private final RefreshTokenService refreshTokenService;
//
//    @Autowired
//    public  RefreshTokenServiceTest(RefreshTokenService refreshTokenService){
//        this.refreshTokenService = refreshTokenService;
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("유저아이디로 토큰 찾기")
//    public void findByUserId(){
//        Long userId = 1L;
//
//        RefreshToken refreshToken = refreshTokenService.findByUserId(userId);
//
//        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJtYXliZWtraW5pIiwiaWF0IjoxNjkzNzkzNTE5LCJleHAiOjE2OTUwMDMxMTksInN1YiI6ImRsZ3VkdG1kMTAyMkBnbWFpbC5jb20iLCJyb2xlIjoiVVNFUiIsImlkIjoxfQ.FvaDkGGwM9U9u8YVOAaSBjBKvKlu7ChfshGpUi4BDHo", refreshToken.getRefreshToken());
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("토큰 삭제 유저아이디로")
//    public void deleteTest(){
//        Long userId = 1L;
//
//        refreshTokenService.deleteRefreshTokenByUserId(userId);
//
//        RefreshToken refreshToken = refreshTokenService.findByUserId(userId);
//
//        assertNull(refreshToken);
//
//    }
//}
