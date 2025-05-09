package com.swyp.backend.itinerary.route.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class RouteTimeResponse {
    private Long walkingDuration;

    private Long drivingDuration;
}
