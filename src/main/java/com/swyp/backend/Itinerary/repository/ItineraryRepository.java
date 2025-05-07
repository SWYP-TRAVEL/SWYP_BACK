package com.swyp.backend.Itinerary.repository;

import com.swyp.backend.Itinerary.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    Optional<Itinerary> findById(Long itineraryId);
    List<Itinerary> findAllByCreatedById(Long userId);
}
