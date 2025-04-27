package com.swyp.backend.auth.client;

import com.swyp.backend.auth.dto.KakaoUnlinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoClient", url = "https://kapi.kakao.com")
public interface KakaoFeignClient {

    @PostMapping(value = "/v1/user/unlink", consumes = "application/x-www-form-urlencoded")
    KakaoUnlinkResponse unlinkUser(
            @RequestHeader("Authorization") String adminKeyAuthorization,
            @RequestParam("target_id_type") String targetIdType,
            @RequestParam("target_id") String targetId
    );
}

