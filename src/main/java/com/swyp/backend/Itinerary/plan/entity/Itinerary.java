package com.swyp.backend.Itinerary.plan.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="itinerary")
@Data
@ToString(exclude = {"dailySchedule", "createdBy"})
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="is_public", nullable = false)
    private boolean isPublic = false;

    @Column(name="is_saved", nullable = false)
    private boolean isSaved = false;

    @Column(name="created_at", nullable = false)
    private LocalDate createdAt;

    // 연관관계가 너무 복잡함
    @OneToOne(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private ItineraryDetail itineraryDetail;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "itinerary_id")
    private List<DailySchedule> dailySchedule = new ArrayList<>();
}
