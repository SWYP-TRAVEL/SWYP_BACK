package com.swyp.backend.user.repository;

import com.swyp.backend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(Long kakaoId);
    Optional <User> findByRefreshToken(String refreshToken);
    void deleteByKakaoId(Long kakaoId);
}
