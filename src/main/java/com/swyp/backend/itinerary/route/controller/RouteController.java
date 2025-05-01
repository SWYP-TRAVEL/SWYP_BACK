package com.swyp.backend.itinerary.route.controller;

import com.swyp.backend.itinerary.route.dto.RouteTimeRequest;
import com.swyp.backend.itinerary.route.dto.RouteTimeResponse;
import com.swyp.backend.itinerary.route.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/route")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/time")
    public ResponseEntity<RouteTimeResponse> getRouteTime(@RequestBody RouteTimeRequest routeTimeRequest) {
        return ResponseEntity.ok(routeService.getRouteTime(routeTimeRequest));
    }
}
