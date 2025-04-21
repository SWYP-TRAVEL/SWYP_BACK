package com.swyp.backend.auth.controller;


import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.dto.SocialLoginRequest;
import com.swyp.backend.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final KakaoService kakaoService;

    @PostMapping("/kakao")
    public ResponseEntity<KakaoTokenResponse> kakaoLogin(@RequestBody SocialLoginRequest request) {
        try {
            KakaoTokenResponse jwt = kakaoService.kakaoLogin(request.getCode());
            // refreshToken → HttpOnly 쿠키에
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwt.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .sameSite("None")
                    .maxAge(60 * 60 * 24 * 7) // 7일
                    .build();
            // accessToken
            KakaoTokenResponse responseBody = KakaoTokenResponse.builder()
                    .accessToken(jwt.getAccessToken())
                    .expiresIn(jwt.getExpiresIn())
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .body(responseBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
