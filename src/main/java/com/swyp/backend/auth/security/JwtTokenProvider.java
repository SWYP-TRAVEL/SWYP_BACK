package com.swyp.backend.auth.security;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.service.KakaoService;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKey;
    private final UserRepository userRepository;

    private final Long tokenValidaity = 1000*60*1L; //1분

    public KakaoTokenResponse createToken(String kakaoId, String name){
        Claims claims = Jwts.claims().setSubject(kakaoId);
        claims.put("name",name);
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime()+tokenValidaity);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidaity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + tokenValidaity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return KakaoTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(accessTokenExpiresIn.getTime())
                .build();
    }
    public boolean validateToken(String jwtToken){
        try{
            Jws<Claims> claims = Jwts.parser().
                    setSigningKey(secretKey).
                    parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(Exception e){
            return false;
        }
    }
    public KakaoTokenResponse reissueToken(String refreshToken){
        User user= userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new RuntimeException("invalid refresh token"));

        Claims claims = Jwts.claims();
        claims.setSubject(user.getKakaoId().toString());
        claims.put("name", user.getName());

        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime()+tokenValidaity);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidaity))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return KakaoTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

}
