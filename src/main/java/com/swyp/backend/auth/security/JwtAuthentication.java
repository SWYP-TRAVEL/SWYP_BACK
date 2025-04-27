package com.swyp.backend.auth.security;

import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.auth.service.KakaoService;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class JwtAuthentication {
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    public Authentication getAuthentication(String jwt) {
        try {
            KakaoUserDTO kakaoUserDTO = kakaoService.getUserInfoWithToken(jwt);
            Long kakaoId = kakaoUserDTO.getId();
            Optional<User> userOptional = userRepository.findByKakaoId(kakaoId);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("not registered user");
            }
            return new UsernamePasswordAuthenticationToken(
                    kakaoId,
                    "",
                    Collections.emptyList()
            );
        } catch (Exception e) {
            throw new RuntimeException("authentication failed", e);
        }
    }
}
