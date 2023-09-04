package com.example.kinnibackend.user;

import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import com.example.kinnibackend.service.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.example.kinnibackend.entity.Users;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersServiceTest {

    private final UserService userService;

    @Autowired
    public UsersServiceTest(UserService userService){
        this.userService = userService;
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
}
