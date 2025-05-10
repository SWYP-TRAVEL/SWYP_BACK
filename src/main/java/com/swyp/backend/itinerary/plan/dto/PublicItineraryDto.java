package com.swyp.backend.itinerary.plan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PublicItineraryDto {
    private Long id;
    private String title;
    @JsonProperty("image_url")
    private String imageUrl;
}
