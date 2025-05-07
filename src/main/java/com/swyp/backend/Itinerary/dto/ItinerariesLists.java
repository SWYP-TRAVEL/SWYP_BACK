package com.swyp.backend.Itinerary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ItinerariesLists {
    private Long id;
    private String title;
    @JsonProperty("image_url")
    private List<String> imageUrl;
}
