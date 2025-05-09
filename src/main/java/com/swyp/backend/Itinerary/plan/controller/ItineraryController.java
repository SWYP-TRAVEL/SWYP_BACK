package com.swyp.backend.Itinerary.plan.controller;

import com.swyp.backend.Itinerary.plan.dto.ItineraryResponse;
import com.swyp.backend.Itinerary.plan.service.ItineraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/itinerary")
public class ItineraryController {

    @Autowired
    private final ItineraryService itineraryService;

    @PostMapping("/{id}")
    public ResponseEntity<List<ItineraryResponse>> getItineraryLists(@PathVariable Long id){
        return ResponseEntity.ok(itineraryService.getItinerary(id));
    }

}
