package com.swyp.backend.itinerary.plan.dto;

import lombok.*;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplaceAttractionRequestDto {
    private long itineraryId;
    private int day;             // 며칠 차
    private int attractionIdx;   // 몇 번째 장소인지
    private long cnt;            // 몇 번째 교체인지 (1~3)
}
