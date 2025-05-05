package com.swyp.backend.Itinerary.controller;
import com.swyp.backend.Itinerary.dto.ItineraryScheduleResponse;
import com.swyp.backend.Itinerary.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/lists/{id}")
    public ResponseEntity<List<ItineraryScheduleResponse>> getItineraryDetail(@PathVariable("id") Long itineraryId) {
        List<ItineraryScheduleResponse> schedules = itineraryService.getItinerarySchedules(itineraryId);
        return ResponseEntity.ok(schedules);
    }

}
