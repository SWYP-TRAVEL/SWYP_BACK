package com.swyp.backend.auth.security;

import com.swyp.backend.auth.dto.KakaoTokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${custom.jwt.secretKey}")
    private String secretKey;

    private final Long tokenValidaity = 1000*60*30L;

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
}
