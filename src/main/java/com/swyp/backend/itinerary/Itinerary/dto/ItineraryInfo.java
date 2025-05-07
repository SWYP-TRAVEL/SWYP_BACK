package com.swyp.backend.itinerary.plan.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItineraryInfo {
    private Long id;
    private String title;
    private List<String> imageUrls;
    private String travelWith;
    private String style;
    private Date startDate;
    private Integer duration;
}
