package com.swyp.backend.Itinerary.route.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="route_times")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteTime {

    @Id
    private Long id;

    @Column(name="start_attraction_id", nullable=false)
    private Long startAttractionId;

    @Column(name="end_attraction_id", nullable=false)
    private Long endAttractionId;

    @Column(name="walking_duration", nullable=false)
    private Long walkingDuration;

    @Column(name="driving_duration", nullable=false)
    private Long drivingDuration;

}
