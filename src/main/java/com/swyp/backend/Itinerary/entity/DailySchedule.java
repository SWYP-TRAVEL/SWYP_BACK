package com.swyp.backend.Itinerary.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name="daily_schedule")
@ToString(exclude = {"itinerary", "attraction"})
public class DailySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="itinerary_id")
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="attraction_id")
    private Attraction attraction;

    @Column(name="day_date")
    private Integer dayDate;

}
