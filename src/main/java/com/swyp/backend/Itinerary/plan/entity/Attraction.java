package com.swyp.backend.Itinerary.plan.entity;

import com.swyp.backend.Itinerary.plan.dto.AttractionDto;
import com.swyp.backend.Itinerary.plan.enums.AttractionType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="attraction")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AttractionType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String description;

    @Column(name="cover_image")
    private String coverImage;

    @Column(name="business_time")
    private String businessTime;

    private Double rating;

    public AttractionDto toDto(){
        return AttractionDto.builder()
                .id(id)
                .type(String.valueOf(type))
                .name(name)
                .address(address)
                .description(description)
                .coverImage(coverImage)
                .businessTime(businessTime)
                .rating(rating)
                .build();
    }
}
