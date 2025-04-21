package com.swyp.backend.user.service;
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
        return userRepository.findByKakaoId(String.valueOf(dto.getId()))
                .orElseGet(() -> {
                    User user = new User();
                    user.setKakaoId(String.valueOf(dto.getId()));
                    user.setName(dto.getName());
                    return userRepository.save(user);
                });
    }
}

