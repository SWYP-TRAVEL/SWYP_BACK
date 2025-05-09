package com.swyp.backend.itinerary.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttractionDto {
    private long id;
    private String type;
    private String name;
    private String address;
    private String description;
    private String coverImage;
    private String businessTime;
    private Double rating;
}
