package com.example.kinnibackend.token.jwt;

import com.example.kinnibackend.config.jwt.JwtProperties;
import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.service.user.UserService;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateTokenTest: 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateTokenTest() {
        // given
        Users testUser = userService.save(Users.builder()
                .email("user@gmail.com")
//                .password("test")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getUserId());
    }

    @DisplayName("validToken_invalidTokenTest: 만료된 토큰인 경우에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidTokenTest() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }


    @DisplayName("validToken_validTokenTest(): 유효한 토큰인 경우에 유효성 검증에 성공한다.")
    @Test
    void validToken_validTokenTest() {
        // given
        String token = JwtFactory.withDefaultValues()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }


    @DisplayName("getAuthenticationTest : 토큰 기반으로 인증정보를 가져올 수 있다.")
    @Test
    void getAuthenticationTest() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserIdTest: 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserIdTest() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when
        Long userIdByToken = tokenProvider.getUserIdFromToken(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}
