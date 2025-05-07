package com.swyp.backend.itinerary.plan.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name="itinerary_detail")
@Data
@ToString(exclude = "itinerary")
public class ItineraryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", nullable = false)
    private Itinerary itinerary;

    @Column(name="travel_with")
    private String travelWith;

    private String style;

    @Column(name="start_date")
    private LocalDate startDate;

    private Integer duration;

    @Column(name = "image_url")
    private String imageUrl;
}
