package com.swyp.backend.auth.controller;


import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.dto.SocialLoginRequest;
import com.swyp.backend.auth.security.JwtTokenProvider;
import com.swyp.backend.auth.service.KakaoService;
import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/kakao")
    public ResponseEntity<KakaoTokenResponse> kakaoLogin(@RequestBody SocialLoginRequest request) {
        try {
            KakaoTokenResponse jwt = kakaoService.kakaoLogin(request.getCode());
            // refreshToken → HttpOnly 쿠키
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", jwt.getRefreshToken())
                    .httpOnly(true)
                    .secure(false) // test
                    .path("/")
                    .sameSite("None")
                    .maxAge(60 * 60 * 24 * 15) // 15일
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

    @PostMapping("/token/reissue")
    public ResponseEntity<KakaoTokenResponse> reissue(HttpServletRequest request) {
        String refreshToken = extractFromCookie(request);
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            KakaoTokenResponse newTokens = jwtTokenProvider.reissueToken(refreshToken);
            return ResponseEntity.ok(newTokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/unlink")
    public ResponseEntity<?> unlinkKakaoUser(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            String jwtAccessToken = authorizationHeader.replace("Bearer ","");
            kakaoService.unlinkUser(jwtAccessToken);
            return ResponseEntity.ok().body("탈퇴 성공");
        } catch (FeignException e) { // Kakao API 요청하다 실패했을 때
            // FeignException은 statusCode + responseBody를 가지고 있음
            System.out.println("카카오 API 실패 상태코드: " + e.status());
            System.out.println("카카오 API 실패 응답 내용: " + e.contentUTF8());

            return ResponseEntity.status(e.status()).body("Kakao API 실패: " + e.contentUTF8());

        } catch (Exception e) { // 그 외 일반적인 예외
            e.printStackTrace();
            return ResponseEntity.status(500).body("탈퇴 실패: " + e.getMessage());
        }
    }

    public String extractFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("refreshToken".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }



}
