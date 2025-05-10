package com.swyp.backend.itinerary.plan.controller;

import com.swyp.backend.itinerary.plan.dto.ItineraryResponse;
import com.swyp.backend.itinerary.plan.dto.PlanPreviewDto;
import com.swyp.backend.itinerary.plan.dto.PublicItineraryDto;
import com.swyp.backend.itinerary.plan.dto.UserPlanInputDto;
import com.swyp.backend.itinerary.plan.service.ItineraryService;
import com.swyp.backend.itinerary.plan.service.PlanGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/Itinerary")
public class ItineraryController {

    @Autowired
    private final ItineraryService itineraryService;
    private final PlanGenerator planGenerator;

    @PostMapping("/{id}")
    public ResponseEntity<List<ItineraryResponse>> getItineraryLists(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.getItinerary(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> makeItineraryPublic(@PathVariable Long id,
                                                    @RequestBody Boolean isSave,
                                                    @RequestBody Boolean isPublic){
        if(isSave) itineraryService.saveItinerary(id);
        if(isPublic) itineraryService.makeItineraryPublic(id);
        return ResponseEntity.ok(null);
    }
    @GetMapping("/preview")
    public ResponseEntity<List<PlanPreviewDto>> previewItinerary(@ModelAttribute UserPlanInputDto userPlanInputDto) {
        return ResponseEntity.ok(planGenerator.generateRecommendDestinations(userPlanInputDto));
    }

    // public 여행지 (limit개)
    @GetMapping("/public")
    public ResponseEntity<List<PublicItineraryDto>> getRandomItineraries(@RequestParam(defaultValue = "10") int limit) {
        List<PublicItineraryDto> itineraries = itineraryService.getRandomPublicItineraries(limit);
        return ResponseEntity.ok(itineraries);
    }


    //여행지 새로 고침
//    @PostMapping("/replace")
//    public ResponseEntity<?>replaceAttraction(@RequestBody ReplaceAttractionRequestDto input){
//        if (input.getCnt() >= 3) {
//            return ResponseEntity
//                    .status(HttpStatus.TOO_MANY_REQUESTS)
//                    .body("새로고침 횟수를 초과했습니다. 더 이상 장소를 변경할 수 없습니다.");
//        }
//        AttractionDto attraction = planGenerator.replaceSingleAttraction(input);
//        return ResponseEntity.ok(attraction);
//    }

}
