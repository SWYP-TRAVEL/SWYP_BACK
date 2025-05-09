package com.swyp.backend.itinerary.route.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
public class RouteTimeRequest {
    private Long startAttractionId;

    private Long endAttractionId;
}
