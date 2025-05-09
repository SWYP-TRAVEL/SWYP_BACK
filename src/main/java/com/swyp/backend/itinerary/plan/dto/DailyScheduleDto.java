package com.swyp.backend.itinerary.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyScheduleDto {
    private Long id;
    private Long itineraryId;
    private List<AttractionDto> attractions;
    private Integer dayDate;
}
