package com.swyp.backend.Itinerary.repository;

import com.swyp.backend.Itinerary.entity.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {
    List<DailySchedule> findAllByItineraryId(Long itineraryId);
}
