package com.swyp.backend.itinerary.planer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttractionDto {
    private long id;
    private String type;
    private String name;
    private String address;
    private String description;
    private String cover_image;
    private String business_time;
    private Double rating;
}
