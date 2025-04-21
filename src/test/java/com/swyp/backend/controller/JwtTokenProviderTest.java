package com.swyp.backend.controller;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.security.JwtTokenProvider;
import com.swyp.backend.auth.service.KakaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private KakaoService kakaoService;

    @Test
    void testCreateToken() {
        String kakaoId = "4224861339";
        String name = "장민지";
        KakaoUserDTO dto = new KakaoUserDTO(Long.parseLong(kakaoId), name);

        KakaoTokenResponse token = jwtTokenProvider.createToken(kakaoId, name);
        KakaoTokenResponse token1 = kakaoService.createJwtFromKakaoUser(dto);
        assertNotNull(token);
        System.out.println("[TEST] JWT 생성 성공: " + token);
        System.out.println("[TEST] JWT 생성 성공: " + token1);
    }
    @Test
    void testValidateToken_withValidToken() {
        String token = jwtTokenProvider.createToken("4224861339", "민지").getAccessToken();
        boolean isValid = jwtTokenProvider.validateToken(token);
        assertTrue(isValid, "[TEST] 유효한 JWT여야 합니다");
        System.out.println("[TEST] 유효성 검사 통과한 토큰: " + token);
    }

}
