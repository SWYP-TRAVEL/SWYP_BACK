package com.swyp.backend.Itinerary.service;

import com.swyp.backend.Itinerary.dto.ItinerariesLists;
import com.swyp.backend.Itinerary.dto.ItineraryScheduleResponse;
import com.swyp.backend.Itinerary.entity.Attraction;
import com.swyp.backend.Itinerary.entity.DailySchedule;
import com.swyp.backend.Itinerary.entity.Itinerary;
import com.swyp.backend.Itinerary.entity.ItineraryDetail;
import com.swyp.backend.Itinerary.repository.DailyScheduleRepository;
import com.swyp.backend.Itinerary.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final DailyScheduleRepository dailyScheduleRepository;

    // 마이페이지 - 여행 리스트 조회
    public List<ItinerariesLists> getItinerariesByUserId(Long userId){
        List<Itinerary> itineraries = itineraryRepository.findAllByCreatedById(userId);
        return itineraries.stream()
                .map(itinerary -> {
                    ItineraryDetail detail = itinerary.getItineraryDetail();
                    List<String> imageUrls = new ArrayList<>();
                    if(detail !=null && detail.getImageUrl() !=null){
                        imageUrls.add(detail.getImageUrl());
                    }
                    return new ItinerariesLists(
                            itinerary.getId(),
                            itinerary.getTitle(),
                            imageUrls
                    );
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
