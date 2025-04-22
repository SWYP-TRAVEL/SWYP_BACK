package com.swyp.backend.controller;

import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.service.KakaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KakaoUserInfoTest {
    @Autowired
    private KakaoService kakaoService;

    @Test
    void testGetUserInfo() throws Exception {
        String accessToken = "xK8FmzfUNfr7Y1d_lgymBYwu5JZbTPyaAAAAAQoXC9cAAAGWW-x3RSHmgQBvj-MV";
        KakaoUserDTO user = kakaoService.getUserInfoWithToken(accessToken);

        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());

        System.out.println("[Test] 사용자 ID: " + user.getId());
        System.out.println("[Test] 사용자 이름: " + user.getName());
    }
}