package com.swyp.backend.Itinerary.plan.repository;

import com.swyp.backend.Itinerary.plan.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
