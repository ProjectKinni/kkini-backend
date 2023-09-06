package com.example.kinnibackend.controller.token;


import com.example.kinnibackend.dto.token.CreateAccessTokenDTO;
import com.example.kinnibackend.dto.token.CreateRefreshTokenDTO;
import com.example.kinnibackend.service.token.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TokenApiController {

    private final TokenService tokenService;

    public TokenApiController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenDTO> createNewAccessToken(@RequestBody CreateRefreshTokenDTO request) {

        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenDTO(newAccessToken));
    }
}
