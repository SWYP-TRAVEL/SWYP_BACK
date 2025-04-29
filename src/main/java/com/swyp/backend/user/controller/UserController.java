package com.swyp.backend.user.controller;


import com.swyp.backend.user.dto.ExperienceRequest;
import com.swyp.backend.user.dto.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    @PostMapping("/experience")
    public ResponseEntity<ExperienceResponse> submitExperience(@RequestBody(required = false) ExperienceRequest experienceRequest) {
        LocalDateTime createdAt = LocalDateTime.now();
        ExperienceResponse response = new ExperienceResponse(
                true,
                experienceRequest.getFeedback(),
                createdAt
        );
        return ResponseEntity.ok(response);
    }
}
