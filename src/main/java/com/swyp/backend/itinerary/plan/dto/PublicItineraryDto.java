package com.swyp.backend.itinerary.plan.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PublicItineraryDto {
    private Long id;
    private String title;
    private String imageUrl;
}
