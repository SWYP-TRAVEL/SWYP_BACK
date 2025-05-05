package com.swyp.backend.plan.entity;


import com.swyp.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="plan_info")
@Data
@ToString(exclude = "plan")
public class PlanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(name="travel_with")
    private String travelWith;

    private String style;

    @Column(name="start_date")
    private LocalDate startDate;

    private Integer duration;

    @Column(name = "image_url")
    private String imageUrl;
}
