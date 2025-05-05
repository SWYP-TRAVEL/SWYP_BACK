package com.swyp.backend.user.controller;


import com.swyp.backend.auth.security.PrincipalDetails;
import com.swyp.backend.plan.dto.ItinerariesLists;
import com.swyp.backend.plan.service.ItineraryService;
import com.swyp.backend.user.dto.ExperienceRequest;
import com.swyp.backend.user.dto.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final ItineraryService itineraryService;
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
    @GetMapping("/mypage/itineraries")
    public ResponseEntity<List<ItinerariesLists>> getUserItineraries(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Long userId = principalDetails.getUser().getId();
        List<ItinerariesLists> itineraries = itineraryService.getItinerariesByUserId(userId);
        return ResponseEntity.ok(itineraries);
    }
}
