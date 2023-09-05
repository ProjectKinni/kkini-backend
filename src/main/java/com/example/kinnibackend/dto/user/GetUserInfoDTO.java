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
public class GetUserInfoDTO {
    private Long userId;
    private String role;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static GetUserInfoDTO fromEntity(Users user) {
        if (user == null) {
            return null; // 또는 다른 적절한 처리
        }

        GetUserInfoDTO userInfoDTO = new GetUserInfoDTO();
        userInfoDTO.setUserId(user.getUserId());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setRole(user.getRole());
        userInfoDTO.setNickname(user.getNickname());

        return userInfoDTO;
    }
}
