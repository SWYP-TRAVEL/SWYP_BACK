package com.swyp.backend.user.service;
import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.user.dto.ExperienceRequest;
import com.swyp.backend.user.dto.ExperienceResponse;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.entity.UserExperience;
import com.swyp.backend.user.repository.UserExperienceRepository;
import com.swyp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserExperienceRepository userExperienceRepository;

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
    public ExperienceResponse saveExperience(Long userId, ExperienceRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        UserExperience experience = new UserExperience();
        experience.setRating(request.getRating());
        experience.setFeedback(request.getFeedback());
        experience.setCreatedAt(LocalDateTime.now());

        experience.setUser(user);
        user.setExperience(experience);

        userExperienceRepository.save(experience);

        return new ExperienceResponse(true, request.getFeedback(), request.getRating(), experience.getCreatedAt());
    }

}

