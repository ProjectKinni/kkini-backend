package com.example.kinnibackend.service.user;

import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public GetUserInfoDTO getUserInfoByUserId(Long userId) {

        Users user = userRepository.findByUserId(userId);

        GetUserInfoDTO userInfoDTO = GetUserInfoDTO.fromEntity(user);

        return userInfoDTO;
    }

    public Users findByUserId(Long userId){
        return userRepository.findByUserId(userId);
    }
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Users save(Users user) {
        return userRepository.save(user);
    }

}