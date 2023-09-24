package com.example.kinnibackend.service.user;

import com.example.kinnibackend.dto.user.UserUpdateDTO;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.repository.product.ProductViewCountRepository;
import com.example.kinnibackend.repository.productLike.ProductLikeRepository;
import com.example.kinnibackend.repository.review.ReviewRepository;
import com.example.kinnibackend.repository.token.RefreshTokenRepository;
import com.example.kinnibackend.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.kinnibackend.dto.user.GetUserInfoDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ProductViewCountRepository productViewCountRepository;
    private final ReviewRepository reviewRepository;
    private final ProductLikeRepository productLikeRepository;

    public GetUserInfoDTO getUserInfoByUserId(Long userId) {

        Users user = userRepository.findByUserId(userId);

        GetUserInfoDTO userInfoDTO = GetUserInfoDTO.fromEntity(user);

        return userInfoDTO;
    }

    public void userUpdate (Long userId, UserUpdateDTO userUpdateDTO){
        Users user = userRepository.findByUserId(userId);

        user.updateNickname(userUpdateDTO.getNickname());

        userRepository.save(user);
    }

    public Users findByUserId(Long userId){
        return userRepository.findByUserId(userId);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public void save(Users user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserByUserId(Long userId){
        productViewCountRepository.deleteByUsers_UserId(userId);
        productLikeRepository.deleteByUsers_UserId(userId);
        reviewRepository.deleteByUsers_UserId(userId);
        refreshTokenRepository.deleteByUserId(userId);
        userRepository.deleteByUserId(userId);
    }

    public boolean isNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }
}