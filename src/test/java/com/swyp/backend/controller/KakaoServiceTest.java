package com.swyp.backend.controller;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.service.KakaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("카카오 인가코드 → AccessToken 발급 테스트")
public class KakaoServiceTest {

    @Autowired
    private KakaoService kakaoService;

    @Test
    void testKakaoAccessToken() throws Exception {
        String testCode = "U6X_Nf0Q3Omj6OKy-TEddp5WY7DovAam-ET_SxdCxMyPxzyRygx4_QAAAAQKDQxeAAABllhxyx3UNEQ5evY1pg";
        KakaoTokenResponse tokenResponse = kakaoService.getAccessToken(testCode);

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        System.out.println("[TEST] AccessToken: " + tokenResponse.getAccessToken());
    }

}
