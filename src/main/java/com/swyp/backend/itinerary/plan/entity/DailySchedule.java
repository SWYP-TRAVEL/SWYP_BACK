package com.swyp.backend.itinerary.plan.entity;

import com.swyp.backend.itinerary.plan.dto.DailyScheduleDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name="daily_schedule")
public class DailySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "itinerary_id")
    private Long itineraryId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="attraction_id")
    private List<Attraction> attractions = new ArrayList<>();

    @Column(name="day_date")
    private Integer dayDate;

    public DailyScheduleDto toDto(){
        return DailyScheduleDto.builder()
                .id(id)
                .itineraryId(itineraryId)
                .attractions(attractions.stream().map(Attraction::toDto).collect(Collectors.toList()))
                .dayDate(dayDate)
                .build();
    }
}
