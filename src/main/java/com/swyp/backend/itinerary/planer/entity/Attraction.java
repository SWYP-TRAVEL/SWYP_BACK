package com.swyp.backend.itinerary.planer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Attraction {
    @Id
    private long id;
    private String type;
    private String name;
    private String address;
    private String description;
    private String cover_image;
    private String business_time;
    private Double rating;
}
