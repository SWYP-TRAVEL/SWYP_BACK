package com.swyp.backend.itinerary.plan.entity;


import com.swyp.backend.itinerary.plan.enums.AttractionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="attraction")
@ToString(exclude = "dailySchedules")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttractionType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String description;

    @Column(name="cover_image")
    private String coverImage;

    @Column(name="business_time")
    private String businessTime;

    private Float rating;

    @OneToMany(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailySchedule> dailySchedules = new ArrayList<>();

}
