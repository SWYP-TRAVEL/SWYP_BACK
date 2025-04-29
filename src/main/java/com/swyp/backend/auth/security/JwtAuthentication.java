package com.swyp.backend.auth.security;

import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.service.KakaoService;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class JwtAuthentication {
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    public Authentication getAuthentication(String jwt) {
        try {
            Long kakaoId = Long.valueOf(kakaoService.extractKakaoIdFromJwt(jwt));
            Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("not registered user");
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    kakaoId,
                    "",
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
            return auth;
        } catch (Exception e) {
            throw new RuntimeException("authentication 실패", e);
        }
    }


}
