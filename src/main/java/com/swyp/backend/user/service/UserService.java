package com.swyp.backend.user.service;
import com.swyp.backend.auth.dto.KakaoTokenResponse;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveOrGetUser(KakaoUserDTO dto) {
        return userRepository.findByKakaoId(dto.getId())
                .orElseGet(() -> {
                    User user = new User();
                    user.setKakaoId(dto.getId());
                    user.setName(dto.getName());
                    return userRepository.save(user);
                });
    }
    public User saveRefreshToken(KakaoUserDTO dto, String refreshToken){
        User user = userRepository.findByKakaoId(dto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRefreshToken(refreshToken);
        return userRepository.save(user);
    }

}

