package com.swyp.backend.itinerary.plan.entity;
import com.swyp.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="plan")
@Data
@ToString(exclude = "planInfos")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name="is_public", nullable = false)
    private boolean isPublic;

    @Column(name="is_saved", nullable = false)
    private boolean isSaved = false;

    // plan_info 테이블
    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanInfo> planInfos = new ArrayList<>();
}
