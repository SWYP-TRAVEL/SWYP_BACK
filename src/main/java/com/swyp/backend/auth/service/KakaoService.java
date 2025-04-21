package com.swyp.backend.auth.service;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {
    private final static String KAKAO_AUTH_URI="https://kauth.kakao.com/oauth/token";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com/v2/user/me";
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;
    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    public KakaoTokenResponse getAccessToken(String code) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URL);
        params.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_AUTH_URI,
                HttpMethod.POST,
                request,
                String.class
        );
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("카카오 access token 요청 실패");
        }

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.getBody());

        String accessToken = (String) json.get("access_token");
        String refreshToken = (String) json.get("refresh_token");
        Object expires = json.get("expires_in");
        Long expiresIn = ((Number) expires).longValue();


        return new KakaoTokenResponse(accessToken, refreshToken, expiresIn);
    }

    public KakaoUserDTO getUserInfoWithToken(String accessToken) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_API_URI,
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObject.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        Long id = (Long) jsonObject.get("id");
        String name = String.valueOf(profile.get("nickname"));

        return new KakaoUserDTO(id, name);
    }

    public KakaoTokenResponse createJwtFromKakaoUser(KakaoUserDTO kakaoUserDTO) {
        String kakaoId = String.valueOf(kakaoUserDTO.getId());
        String name = kakaoUserDTO.getName();
        return jwtTokenProvider.createToken(kakaoId, name);
    }
    public KakaoTokenResponse kakaoLogin(String code) throws Exception{
        KakaoTokenResponse token = getAccessToken(code);
        KakaoUserDTO kakaoUser = getUserInfoWithToken(token.getAccessToken());
        KakaoTokenResponse jwt = createJwtFromKakaoUser(kakaoUser);
        return KakaoTokenResponse.builder()
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .expiresIn(jwt.getExpiresIn())
                .build();
    }


}
