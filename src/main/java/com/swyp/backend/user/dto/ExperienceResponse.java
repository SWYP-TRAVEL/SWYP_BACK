package com.swyp.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ExperienceResponse {
    private boolean success;
    private String feedback;
    private LocalDateTime createdAt;
}

