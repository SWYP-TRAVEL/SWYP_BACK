package com.swyp.backend.controller;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.service.KakaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("카카오 로그인 플로우 통합 테스트")
public class KakaoServiceTest {
    @Autowired
    private KakaoService kakaoService;

    @Test
    @DisplayName("카카오 인가코드로 AccessToken 발급")
    void testGetAccessToken() throws Exception {
        String testCode = "";
        KakaoTokenResponse tokenResponse = kakaoService.getAccessToken(testCode);

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        System.out.println("[TEST] AccessToken: " + tokenResponse.getAccessToken());
    }

    @Test
    @DisplayName("인가코드로 로그인 처리 및 JWT 응답")
    void testKakaoLoginFlow() throws Exception {
        String testCode = "";
        KakaoTokenResponse response = kakaoService.kakaoLogin(testCode);

        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());
        System.out.println("[TEST] JWT AccessToken: " + response.getAccessToken());
        System.out.println("[TEST] JWT RefreshToken: " + response.getRefreshToken());
    }
}

