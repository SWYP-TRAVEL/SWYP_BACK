package com.swyp.backend.itinerary.planer.repository;

import com.swyp.backend.itinerary.planer.entity.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Attraction findById(long id);
}
