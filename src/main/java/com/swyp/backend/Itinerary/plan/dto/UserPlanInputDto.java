package com.swyp.backend.Itinerary.plan.dto;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
@Getter
public class UserPlanInputDto {
    private String travelWith;
    private Date startDate;
    private Date endDate;
    private String description;
}
