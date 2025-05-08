package com.swyp.backend.itinerary.plan.dto;


import lombok.*;
import java.time.LocalDate;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPlanRequirementDto {
    private String travelWith;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String theme;
}
