package com.swyp.backend.itinerary.route.repository;

import com.swyp.backend.itinerary.route.entity.RouteTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteTimeRepository extends JpaRepository<RouteTime, Long> {
    Optional<RouteTime> findByStartAttractionIdAndEndAttractionId(Long startAttractionId, Long endAttractionId);
}
