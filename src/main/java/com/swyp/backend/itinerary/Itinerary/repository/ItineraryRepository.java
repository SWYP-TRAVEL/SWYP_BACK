package com.swyp.backend.itinerary.plan.repository;

import com.swyp.backend.itinerary.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Plan, Long> {

    Optional<Plan> findById(Long planId);
    List<Plan> findAllByCreatedById(Long userId);
}
