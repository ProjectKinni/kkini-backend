package com.example.kinnibackend.config.oauth;

import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.config.util.CookieUtil;
import com.example.kinnibackend.entity.RefreshToken;
import com.example.kinnibackend.entity.Users;
import com.example.kinnibackend.service.token.RefreshTokenService;
import com.example.kinnibackend.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public static final String  ACCESS_TOKEN_COOKIE_NAME = "access_token";

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);

    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

    public static final String REDIRECT_PATH = "http://kkini.net:8080/";

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;

    // 소셜 로그인에 성공되면 실행되는 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = null;

        if (attributes.containsKey("email")) {
            email = (String) attributes.get("email");
        } else if (attributes.containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount.containsKey("email")) {
                email = (String) kakaoAccount.get("email");
            }
        } else if (attributes.containsKey("response")) {
            Map<String, Object> responseAttributes = (Map<String, Object>) attributes.get("response");
            if (responseAttributes.containsKey("email")) {
                email = (String) responseAttributes.get("email");
            }
        }

        if (email == null) {
            throw new RuntimeException("Email not found in OAuth2 attributes");
        }

        Users user = userService.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
//        addRefreshTokenToCookie(request, response, refreshToken);
        saveRefreshToken(user.getUserId(), refreshToken);

        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        addAccessTokenToCookie(request, response, accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, REDIRECT_PATH);
    }

    // 생성된 refreshToken을 DB에 저장하는 메서드
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenService.findByUserId(userId);

        if(refreshToken != null){
            refreshToken.update(newRefreshToken);
        } else {
            refreshToken = new RefreshToken(userId, newRefreshToken);
        }

        refreshTokenService.save(refreshToken);
    }

    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void addAccessTokenToCookie(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        int cookieMaxAge = (int) ACCESS_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, ACCESS_TOKEN_COOKIE_NAME, accessToken, cookieMaxAge);
    }


    // 소셜 로그인 인증 과정에서 셍긴 임시 데이터를 삭제 (정리되지 않은 데이터로 부터의 보안 문제)
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
