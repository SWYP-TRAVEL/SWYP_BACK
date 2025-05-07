package com.swyp.backend.itinerary.plan.repository;

import com.swyp.backend.itinerary.plan.entity.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DailyScheduleRepository extends JpaRepository<DailySchedule, Long> {
    List<DailySchedule> findAllByItineraryId(Long itineraryId);
}
