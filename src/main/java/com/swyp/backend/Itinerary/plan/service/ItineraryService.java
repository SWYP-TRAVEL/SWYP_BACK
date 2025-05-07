package com.swyp.backend.itinerary.plan.service;

import com.swyp.backend.itinerary.plan.dto.ItinerariesLists;
import com.swyp.backend.itinerary.plan.dto.ItineraryScheduleResponse;
import com.swyp.backend.itinerary.plan.entity.Attraction;
import com.swyp.backend.itinerary.plan.entity.DailySchedule;
import com.swyp.backend.itinerary.plan.entity.PlanInfo;
import com.swyp.backend.itinerary.plan.entity.Plan;
import com.swyp.backend.itinerary.plan.repository.ItineraryRepository;
import com.swyp.backend.itinerary.plan.repository.DailyScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final DailyScheduleRepository dailyScheduleRepository;


    // 여행 리스트 조회
    public List<ItinerariesLists> getItinerariesByUserId(Long userId){
        List<Plan> plans = itineraryRepository.findAllByCreatedById(userId);
        return plans.stream()
                .map(plan ->{
                    List<String> imageUrls = plan.getPlanInfos().stream()
                            .map(PlanInfo::getImageUrl)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    return new ItinerariesLists(plan.getId(), plan.getTitle(), imageUrls);
                })
                .collect(Collectors.toList());
    }

    // 메인 페이지 - 여행 코스 상세 조회
    public List<ItineraryScheduleResponse> getItinerarySchedules(Long itineraryId){
        List<DailySchedule> schedules = dailyScheduleRepository.findAllByItineraryId(itineraryId);
        return schedules.stream()
                .map(schedule -> {
                    Attraction attraction = schedule.getAttraction();
                    return new ItineraryScheduleResponse(
                            attraction.getId(),
                            attraction.getType(),
                            attraction.getName(),
                            attraction.getAddress(),
                            attraction.getDescription(),
                            attraction.getCoverImage(),
                            attraction.getBusinessTime(),
                            attraction.getRating()
                    );
                })
                .collect(Collectors.toList());
    }


}
