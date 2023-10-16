package com.example.kinnibackend.config;

import com.example.kinnibackend.config.jwt.TokenProvider;
import com.example.kinnibackend.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.kinnibackend.config.oauth.OAuth2SuccessHandler;
import com.example.kinnibackend.config.oauth.OAuth2UserCustomService;
import com.example.kinnibackend.service.token.RefreshTokenService;
import com.example.kinnibackend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig {

    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    // 시큐리티
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(httpSecurityCorsConfigurer -> {});

        http.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        http.sessionManagement(sessionManagementConfigurer-> {
            sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(authorize -> {
            authorize
//                    .requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("/logout")).permitAll()
//                    .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated()
//                    .requestMatchers(new AntPathRequestMatcher("/getUserInfo")).hasAuthority("USER")
//                    .requestMatchers(new AntPathRequestMatcher("/main")).hasRole("ADMIN")
                    .anyRequest().permitAll();
        });

        http.oauth2Login(oauth2  -> {
            oauth2.loginPage("/getUserInfo")
                    .authorizationEndpoint(endpointConfig -> endpointConfig
                            .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                    .successHandler(oAuth2SuccessHandler())
                    .userInfoEndpoint( userInfoConfig -> userInfoConfig
                            .userService(oAuth2UserCustomService));
        });

        http.logout(logoutConfig -> {
            logoutConfig
                    .logoutSuccessUrl("http://localhost:3000/");
        });

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer-> {
            httpSecurityExceptionHandlingConfigurer.defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    new AntPathRequestMatcher("/api/**"));
        });

        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {

        return new OAuth2SuccessHandler(tokenProvider,
                refreshTokenService,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

}
