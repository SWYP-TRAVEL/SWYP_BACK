package com.swyp.backend.auth.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KakaoTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
