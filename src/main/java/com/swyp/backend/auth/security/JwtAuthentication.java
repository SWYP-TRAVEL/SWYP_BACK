package com.swyp.backend.auth.security;

import com.swyp.backend.auth.service.KakaoService;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtAuthentication {
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    public Authentication getAuthentication(String jwt) {
        try {
            Long kakaoId = Long.valueOf(kakaoService.extractKakaoIdFromJwt(jwt));
            User user = userRepository.findByKakaoId(kakaoId)
                    .orElseThrow(()-> new UsernameNotFoundException("not registered user"));
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            return new UsernamePasswordAuthenticationToken(
                    principalDetails,
                    null,
                    principalDetails.getAuthorities()
            );
        } catch (Exception e) {
            throw new RuntimeException("authentication 실패", e);
        }
    }


}
