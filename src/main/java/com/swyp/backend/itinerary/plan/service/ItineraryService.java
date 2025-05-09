package com.swyp.backend.itinerary.plan.service;

import com.swyp.backend.itinerary.plan.dto.ItinerariesLists;
import com.swyp.backend.itinerary.plan.dto.ItineraryResponse;
import com.swyp.backend.itinerary.plan.entity.Itinerary;
import com.swyp.backend.itinerary.plan.entity.Attraction;
import com.swyp.backend.itinerary.plan.entity.DailySchedule;
import com.swyp.backend.itinerary.plan.repository.DailyScheduleRepository;
import com.swyp.backend.itinerary.plan.repository.ItineraryRepository;
import com.swyp.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItineraryService {
    private final ItineraryRepository itineraryRepository;
    private final DailyScheduleRepository dailyScheduleRepository;
    private final UserRepository userRepository;


    // 여행 리스트 조회
    public List<ItinerariesLists> getItinerariesByUserId(Long userId){
        List<Itinerary> itineraries = itineraryRepository.findAllByCreatedBy(userId);
        return itineraries.stream()
                .map(itinerary ->{
                    List<String> imageUrls = itinerary.getDailySchedule().stream()
                            .flatMap(daily -> daily.getAttractions().stream()) // DailySchedule → Attraction
                            .map(Attraction::getCoverImage)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    return new ItinerariesLists(itinerary.getId(), itinerary.getTitle(), imageUrls);
                })
                .collect(Collectors.toList());
    }

    // 여행 코스 상세 조회
    public List<ItineraryResponse> getItinerary(Long itineraryId){
        List<Itinerary> itineraries = itineraryRepository.findAllByCreatedBy(itineraryId);

        List<ItineraryResponse> responses = new ArrayList<>();
        for(Itinerary itinerary : itineraries){
            ItineraryResponse itineraryResponse = ItineraryResponse.builder()
                    .id(itinerary.getId())
                    .createdAt(itinerary.getCreatedAt())
                    .createdBy(userRepository.findById(itinerary.getCreatedBy()).get())
                    .isSaved(itinerary.isSaved())
                    .isPublic(itinerary.isPublic())
                    .dailyScheduleDtos(itinerary.getDailySchedule().stream().map(DailySchedule::toDto).collect(Collectors.toList()))
                    .build();
            responses.add(itineraryResponse);
        }
        return responses;
    }

    public boolean makeItineraryPublic(Long itineraryId){
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElse(null);
        if(itinerary == null){
            return false;
        }
        itineraryRepository.save(itinerary);
        return true;
    }

    public boolean saveItinerary(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId).orElse(null);
        if(itinerary == null){
            return false;
        }
        itinerary.setSaved(true);
        itineraryRepository.save(itinerary);
        return true;
    }

}
