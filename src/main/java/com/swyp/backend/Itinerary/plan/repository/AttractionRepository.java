package com.swyp.backend.itinerary.plan.repository;

import com.swyp.backend.itinerary.plan.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
}
