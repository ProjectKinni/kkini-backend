package com.example.kinnibackend.config.oauth;

import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final UserService userService;

    // Security의 소셜 로그인 인증을 처리하는 메서드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user, userRequest.getClientRegistration().getRegistrationId());
        return user;
    }


    // 소셜 로그인한 회원의 정보를 바탕으로 서비스에 새로운 유저 DB에 저장

    private Users saveOrUpdate(OAuth2User oAuth2User, String registrationId) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email;
        String nickname;

         String role = "USER"; // 해당 부분 수정 시 권한 하드코딩

        if(registrationId.equals("google")) {
            email = (String) attributes.get("email");
            nickname = (String) attributes.get("name");
        } else if(registrationId.equals("naver")) {
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");

            email = (String) responseAttributes.get("email");
            nickname = (String) responseAttributes.get("name");
        }else {
            Map<String, Object> kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profileAttributes = (Map<String, Object>) kakaoAccountAttributes.get("profile");

            email = (String) kakaoAccountAttributes.get("email");
            nickname = (String) profileAttributes.get("nickname");
        }
        Users user = userService.findByEmail(email);

        if(user == null) { // 없는 유저면 가입용으로 새로 객체 생성
           user = Users.builder()
                    .email(email)
                    .nickname(nickname)
                    .role(role)
                    .build();
        }
        return userService.save(user);
    }
}