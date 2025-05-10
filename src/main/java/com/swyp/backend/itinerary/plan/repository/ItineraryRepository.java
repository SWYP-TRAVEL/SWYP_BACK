package com.swyp.backend.itinerary.plan.repository;

import com.swyp.backend.itinerary.plan.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Long> {

    Optional<Itinerary> findById(Long planId);
    List<Itinerary> findAllByCreatedBy(Long id);
    @Query(value = "SELECT * FROM itinerary WHERE is_public = true ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Itinerary> findRandomPublicItineraries(@Param("limit") int limit);

}
