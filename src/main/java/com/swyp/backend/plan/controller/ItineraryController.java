package com.swyp.backend.plan.controller;

import com.swyp.backend.auth.security.PrincipalDetails;
import com.swyp.backend.plan.dto.ItinerariesLists;
import com.swyp.backend.plan.dto.ItineraryInfo;
import com.swyp.backend.plan.service.ItineraryService;
import com.swyp.backend.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {

    private final ItineraryService itineraryService;

    //여행 코스 저장
//    @PostMapping("/save")
//    public saveItinerary(){
//        return
//    }

    // 여행 코스들 조회 - isPublic
//    @GetMapping("/lists")
//    public ResponseEntity<List<ItinerariesLists>> getItineraryLists(){
//        return ResponseEntity.ok();
//    }


    // 해당 코스 상세 조회 -> 코스 id로 조회
//    @GetMapping("/lists/{id}")
//    public ResponseEntity<ItineraryInfo> getItineraryDetail(@PathVariable Long id) {
//        ItineraryInfo info = itineraryService.getItineraryDetailById(id);
//        return ResponseEntity.ok(info);
//    }
}
