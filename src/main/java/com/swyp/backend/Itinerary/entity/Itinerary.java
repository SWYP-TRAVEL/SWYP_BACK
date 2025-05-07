package com.swyp.backend.Itinerary.entity;
import com.swyp.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="itinerary")
@Data
@ToString(exclude = {"itineraryDetail","dailySchedule", "createdBy"})
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name="is_public", nullable = false)
    private boolean isPublic = false;

    @Column(name="is_saved", nullable = false)
    private boolean isSaved = false;

    @Column(name="created_at", nullable = false)
    private LocalDate createdAt;

    @OneToOne(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private ItineraryDetail itineraryDetail;

    @OneToMany(mappedBy="itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailySchedule> dailySchedule = new ArrayList<>();
}
