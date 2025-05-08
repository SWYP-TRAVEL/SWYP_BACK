package com.swyp.backend.itinerary.plan.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPlanInputDto {
    private String travelWith;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
}
