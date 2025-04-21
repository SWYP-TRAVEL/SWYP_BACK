package com.swyp.backend.controller;

import com.swyp.backend.auth.dto.KakaoUserDTO;
import com.swyp.backend.user.entity.User;
import com.swyp.backend.user.repository.UserRepository;
import com.swyp.backend.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("UserService - 카카오 사용자 저장 테스트")
class UserSaveTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @AfterEach
    void tearDown() {
        userRepository.deleteAll(); // 테스트 후 DB 정리
    }

    @Test
    @DisplayName("새로운 카카오 사용자가 저장되어야 한다")
    void saveNewKakaoUser() {
        KakaoUserDTO dto = new KakaoUserDTO(4224861339L, "장민지");
        User savedUser = userService.saveOrGetUser(dto);
        assertNotNull(savedUser.getId());
        assertEquals("장민지", savedUser.getName());
        assertEquals("4224861339", savedUser.getKakaoId());
    }

    @Test
    @DisplayName("동일한 카카오 ID는 중복 저장되지 않아야 한다")
    void saveDuplicateKakaoUser() {
        // given
        KakaoUserDTO dto1 = new KakaoUserDTO(4224861339L, "장민지");
        KakaoUserDTO dto2 = new KakaoUserDTO(4224861339L, "장민지");
        User user1 = userService.saveOrGetUser(dto1);
        User user2 = userService.saveOrGetUser(dto2);
        assertEquals(user1.getId(), user2.getId()); // 같은 사용자여야 함
        assertEquals(1, userRepository.count());   // DB에 하나만 존재해야 함
    }
}
