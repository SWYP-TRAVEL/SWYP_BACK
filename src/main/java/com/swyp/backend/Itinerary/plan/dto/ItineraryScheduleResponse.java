package com.swyp.backend.itinerary.plan.dto;
import com.swyp.backend.itinerary.plan.enums.AttractionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryScheduleResponse {
    private Long id;
    private AttractionType type;
    private String name;
    private String address;
    private String description;
    private String coverImage;
    private String businessTime;
    private Float rating;
}
