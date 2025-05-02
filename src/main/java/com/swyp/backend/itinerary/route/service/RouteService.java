package com.swyp.backend.itinerary.route.service;

import com.swyp.backend.itinerary.planer.dto.AttractionDto;
import com.swyp.backend.itinerary.planer.entity.Attraction;
import com.swyp.backend.itinerary.planer.repository.AttractionRepository;
import com.swyp.backend.itinerary.route.dto.Coordinates;
import com.swyp.backend.itinerary.route.dto.RouteTimeRequest;
import com.swyp.backend.itinerary.route.dto.RouteTimeResponse;
import com.swyp.backend.itinerary.route.entity.RouteTime;
import com.swyp.backend.itinerary.route.repository.RouteTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    private final RouteTimeRepository routeTimeRepository;
    private final MapApiService mapApiService;
    private final AttractionRepository attractionRepository;

    @Autowired
    public RouteService(RouteTimeRepository routeTimeRepository, MapApiService mapApiService, AttractionRepository attractionRepository) {
        this.routeTimeRepository = routeTimeRepository;
        this.mapApiService = mapApiService;
        this.attractionRepository = attractionRepository;
    }


    public RouteTimeResponse getRouteTime(RouteTimeRequest dto) {
        Attraction start = attractionRepository.findById(dto.getStartAttractionId()).orElseThrow();
        Attraction end = attractionRepository.findById(dto.getEndAttractionId()).orElseThrow();

        AttractionDto startDto = AttractionDto.builder().id(start.getId()).address(start.getAddress()).build();
        AttractionDto endDto = AttractionDto.builder().id(end.getId()).address(end.getAddress()).build();
        return this.getRouteTime(startDto, endDto);
    }

    public RouteTimeResponse getRouteTime(AttractionDto start, AttractionDto end) {
        RouteTime entity = routeTimeRepository.findByStartAttractionIdAndEndAttractionId(start.getId(), end.getId())
                .orElse(saveRouteTime(start, end));

        return RouteTimeResponse.builder()
                .walkingDuration(entity.getWalkingDuration())
                .drivingDuration(entity.getDrivingDuration())
                .build();
    }

    private RouteTime saveRouteTime(AttractionDto start, AttractionDto end) {
        long walkingTime = mapApiService.getWalkingTime(start.getAddress(), end.getAddress());
        long drivingTime = mapApiService.getDrivingTime(start.getAddress(), end.getAddress());

        RouteTime entity = RouteTime.builder()
                        .startAttractionId(start.getId())
                        .endAttractionId(end.getId())
                        .walkingDuration(walkingTime)
                        .drivingDuration(drivingTime)
                        .build();
        routeTimeRepository.save(entity);
        return entity;
    }
}
