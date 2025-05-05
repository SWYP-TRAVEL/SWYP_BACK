package com.swyp.backend.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="users_experience")
@Data
public class UserExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String feedback;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

}

