package com.example.kinnibackend.controller.user;

import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import com.example.kinnibackend.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 허용할 도메인을 설정
public class UserController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    // 요청보낸 쿠키의 토큰으로 user정보를 받아 보내주기
    @PostMapping("/getUserInfo")
    public ResponseEntity<GetUserInfoDTO> getUserInfo(@CookieValue(name = "access_token", required = false) String token) {
        if (token == null || !tokenProvider.validToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = tokenProvider.getUserIdFromToken(token);

        GetUserInfoDTO user = userService.getUserInfoByUserId(userId);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader("Authorization");

        Cookie cookie = new Cookie("access_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "로그아웃 성공";
    }


}
