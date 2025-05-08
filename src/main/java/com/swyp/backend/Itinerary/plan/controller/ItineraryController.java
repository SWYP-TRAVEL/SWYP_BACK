package com.swyp.backend.itinerary.plan.controller;

import com.swyp.backend.itinerary.plan.dto.ItineraryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.swyp.backend.itinerary.plan.dto.PlanPreviewDto;
import com.swyp.backend.itinerary.plan.dto.UserPlanInputDto;
import com.swyp.backend.itinerary.plan.service.ItineraryService;
import com.swyp.backend.itinerary.plan.service.PlanGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {

    @Autowired
    private final ItineraryService itineraryService;
    private final PlanGenerator planGenerator;

    @PostMapping("/{id}")
    public ResponseEntity<List<ItineraryResponse>> getItineraryLists(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.getItinerary(id));
    }

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

    @GetMapping("/preview")
    public ResponseEntity<List<PlanPreviewDto>> previewItinerary(@ModelAttribute UserPlanInputDto userPlanInputDto) {
        return ResponseEntity.ok(planGenerator.generateRecommendDestinations(userPlanInputDto));
    }
}
