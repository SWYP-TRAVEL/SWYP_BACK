package com.swyp.backend.auth.service;

import com.swyp.backend.auth.client.KakaoFeignClient;
import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUnlinkResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.security.JwtTokenProvider;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import com.swyp.backend.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
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
    @Value("${custom.jwt.secretKey}")
    private String secretKey;
    @Value("${kakao.admin.key}")
    private String adminKey;
    private final static String KAKAO_AUTH_URI="https://kauth.kakao.com/oauth/token";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com/v2/user/me";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final KakaoFeignClient kakaoFeignClient;
    private final UserRepository userRepository;
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
        return KakaoTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();

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
    public KakaoTokenResponse kakaoLogin(String code) throws Exception {
        KakaoTokenResponse token = getAccessToken(code);
        KakaoUserDTO kakaoUser = getUserInfoWithToken(token.getAccessToken());
        userService.saveOrGetUser(kakaoUser);
        KakaoTokenResponse jwt = createJwtFromKakaoUser(kakaoUser);
        userService.saveRefreshToken(kakaoUser, jwt.getRefreshToken());

        return KakaoTokenResponse.builder()
                .accessToken(jwt.getAccessToken())
                .refreshToken(jwt.getRefreshToken())
                .expiresIn(jwt.getExpiresIn())
                .userName(kakaoUser.getName())
                .build();
    }
    public String extractKakaoIdFromJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt.replace("Bearer ", ""))
                .getBody();
        return claims.getSubject();
    }
    @Transactional
    public void unlinkUser(String accessToken) {
        System.out.println("unlinkUser accessToken: "+ accessToken);
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(accessToken.replace("Bearer ", ""))
                .getBody();
        System.out.println("claims"+claims);
        Long kakaoId = Long.valueOf(claims.getSubject());
        System.out.println("kakaoId"+kakaoId);
        String adminKeyAuthorization = "KakaoAK " + adminKey;
        System.out.println("adminKeyAuthorization: " + adminKeyAuthorization);
        KakaoUnlinkResponse response = kakaoFeignClient.unlinkUser(
                adminKeyAuthorization,
                "user_id",
                kakaoId
        );
        userRepository.deleteByKakaoId(Long.valueOf(kakaoId));
    }
}
