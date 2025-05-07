package com.swyp.backend.user.controller;


import com.swyp.backend.auth.security.PrincipalDetails;
import com.swyp.backend.Itinerary.dto.ItinerariesLists;
import com.swyp.backend.Itinerary.service.ItineraryService;
import com.swyp.backend.user.dto.ExperienceRequest;
import com.swyp.backend.user.dto.ExperienceResponse;
import com.swyp.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final ItineraryService itineraryService;
    private final UserService userService;
    @PostMapping("/experience")
    public ResponseEntity<ExperienceResponse> submitExperience(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody ExperienceRequest experienceRequest) {
        Long userId = principalDetails.getUser().getId();
        ExperienceResponse response = userService.saveExperience(userId, experienceRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/mypage/itineraries")
    public ResponseEntity<List<ItinerariesLists>> getUserItineraries(@AuthenticationPrincipal PrincipalDetails principalDetails){
        Long userId = principalDetails.getUser().getId();
        List<ItinerariesLists> itineraries = itineraryService.getItinerariesByUserId(userId);
        return ResponseEntity.ok(itineraries);
    }
}
