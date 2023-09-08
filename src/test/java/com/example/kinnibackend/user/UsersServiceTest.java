package com.example.kinnibackend.user;

import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import com.example.kinnibackend.dto.user.UserUpdateDTO;
import com.example.kinnibackend.entity.RefreshToken;
import com.example.kinnibackend.service.token.RefreshTokenService;
import com.example.kinnibackend.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.kinnibackend.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsersServiceTest {

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public UsersServiceTest(UserService userService, RefreshTokenService refreshTokenService){
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }
    @Test
    @Transactional
    @DisplayName("이메일로 아이디 찾기")
    public void findUserByEmailTest(){
        String email = "dlgudtmd1022@gmail.com";

        Users user = userService.findByEmail(email);

        assertEquals(1L,user.getUserId());
    }

    @Test
    @Transactional
    @DisplayName("아이디로 아이디 찾기")
    public void findUserByUserIdTest(){
        Long userId = 1L;

        Users user = userService.findByUserId(userId);

        assertEquals("dlgudtmd1022@gmail.com", user.getEmail());
        assertEquals("USER", user.getRole());
    }

    @Test
    @Transactional
    @DisplayName("GetUserInfoTest")
    public void GetUserInfoTest(){
        Long userId = 1L;

        GetUserInfoDTO user = userService.getUserInfoByUserId(userId);

        assertEquals("dlgudtmd1022@gmail.com", user.getEmail());
        assertEquals("USER", user.getRole());
    }

    @Test
    @Transactional
    @DisplayName("유저 저장")
    public void saveTest(){
        String email = "park97@gmail.com";
        String role = "USER";

        Users user = Users.builder()
                .email(email)
                .role(role)
                .build();

        userService.save(user);

        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
    }

    @Test
    @Transactional
    @DisplayName("user update")
    public void userUpdateTest(){
        Long userId = 1L;
        String newNickname = "이형승";

        UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                .nickname(newNickname)
                .build();

        userService.userUpdate(userId, updateDTO);

        Users updatedUser = userService.findByUserId(userId);
        assertEquals(newNickname, updatedUser.getNickname());
    }

    @Test
    @Transactional
    @DisplayName("유저 삭제")
    public void deleteUserByUserIdTest(){
        Long userId = 1L;

        userService.deleteUserByUserId(userId);
        refreshTokenService.deleteRefreshTokenByUserId(userId);

        Users user = userService.findByUserId(userId);
        RefreshToken refreshToken = refreshTokenService.findByUserId(userId);

        assertNull(refreshToken);
        assertNull(user);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 확인")
    public void checkNickname() {
        String availableNickname = "송민지";
        String existingNickname = "이형승";

        // 사용 가능한 닉네임을 확인
        boolean isAvailable = userService.isNicknameAvailable(availableNickname);
        assertTrue(isAvailable); // 사용 가능한 경우 true가 반환되어야 함

        // 이미 존재하는 닉네임을 확인
        boolean isExisting = userService.isNicknameAvailable(existingNickname);
        assertFalse(isExisting); // 이미 존재하는 경우 false가 반환되어야 함
    }
}
