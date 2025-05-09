package com.swyp.backend.itinerary.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Builder
@AllArgsConstructor
public class PlanPreviewDto {
    private String name;
    private String theme;
    private String imageUrl;
}
