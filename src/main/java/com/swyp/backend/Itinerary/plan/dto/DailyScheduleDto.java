package com.swyp.backend.Itinerary.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyScheduleDto {
    private Long id;
    private Long itineraryId;
    private List<AttractionDto> attractions;
    private LocalDate dayDate;
}
