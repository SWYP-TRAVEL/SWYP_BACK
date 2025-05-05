package com.swyp.backend.plan.service;

import com.swyp.backend.plan.dto.ItinerariesLists;
import com.swyp.backend.plan.dto.ItineraryInfo;
import com.swyp.backend.plan.entity.Plan;
import com.swyp.backend.plan.entity.PlanInfo;
import com.swyp.backend.plan.repository.ItineraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItineraryService {
    private final ItineraryRepository itineraryRepository;

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
    // 여행 상세 조회
//    public ItineraryInfo getItineraryDetailById(Long id){
//
//    }


}
