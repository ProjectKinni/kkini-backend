package com.example.kinnibackend.controller.user;

import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import com.example.kinnibackend.dto.user.UserUpdateDTO;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://kkini.net:3000") // 허용할 도메인을 설정
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
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");

        Cookie cookie = new Cookie("access_token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        // 로그아웃 성공을 나타내는 HTTP 상태 코드 200과 메시지를 반환
        return ResponseEntity.ok("로그아웃 성공");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@CookieValue(name = "access_token", required = false) String token) {
        if (token == null || !tokenProvider.validToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = tokenProvider.getUserIdFromToken(token);

        userService.deleteUserByUserId(userId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateUser(@CookieValue(name = "access_token", required = false) String token,
                                           @RequestBody UserUpdateDTO userUpdateDTO) {
        if (token == null || !tokenProvider.validToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = tokenProvider.getUserIdFromToken(token);

        userService.userUpdate(userId, userUpdateDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/isNicknameAvailable")
    public ResponseEntity<Boolean> isNicknameAvailable(
            @CookieValue(name = "access_token", required = false) String token,
            @RequestBody Map<String, String> requestMap) {

        if (token == null || !tokenProvider.validToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String nickname = requestMap.get("nickname");

        boolean isAvailable = userService.isNicknameAvailable(nickname);
        return ResponseEntity.ok(isAvailable);
    }
}