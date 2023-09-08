package com.example.kinnibackend.dto.user;

import com.example.kinnibackend.entity.Users;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class UserUpdateDTO {

    private Long userId;
    private String nickname;

    public static UserUpdateDTO fromEntity(Users user){
        return UserUpdateDTO.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .build();
    }
}
