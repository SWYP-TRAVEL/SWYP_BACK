package com.swyp.backend.itinerary.route.service;

import com.swyp.backend.itinerary.route.dto.RouteTimeRequest;
import com.swyp.backend.itinerary.route.dto.RouteTimeResponse;
import com.swyp.backend.itinerary.route.entity.RouteTime;
import com.swyp.backend.itinerary.route.repository.RouteTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class RouteService {

    private final RouteTimeRepository routeTimeRepository;

    @Autowired
    public RouteService(RouteTimeRepository routeTimeRepository) {
        this.routeTimeRepository = routeTimeRepository;
    }


    public RouteTimeResponse getRouteTime(RouteTimeRequest dto) {
        return this.getRouteTime(dto.getStartAttractionId(), dto.getEndAttractionId());
    }


    public RouteTimeResponse getRouteTime(Long startAttractionId, Long endAttractionId) {
        RouteTime entity = routeTimeRepository.findByStartAttractionIdAndEndAttractionId(startAttractionId, endAttractionId)
                .orElseGet(calcRouteTime(startAttractionId, endAttractionId));

        return RouteTimeResponse.builder()
                .walkingDuration(entity.getWalkingDuration())
                .drivingDuration(entity.getDrivingDuration())
                .build();
    }

    public Supplier<? extends RouteTime> calcRouteTime(Long startAttractionId, Long endAttractionId) {
        return null;
    }
}
