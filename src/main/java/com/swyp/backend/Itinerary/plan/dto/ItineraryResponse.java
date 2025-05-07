package com.swyp.backend.itinerary.plan.dto;
import com.swyp.backend.itinerary.plan.entity.DailySchedule;
import com.swyp.backend.itinerary.plan.enums.AttractionType;
import com.swyp.backend.user.entity.User;
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
public class ItineraryResponse {
    private Long id;
    private User createdBy;
    private LocalDate createdAt;
    private Boolean isPublic;
    private Boolean isSaved;
    private List<DailyScheduleDto> dailyScheduleDtos;
}
