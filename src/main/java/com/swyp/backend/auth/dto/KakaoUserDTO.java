package com.swyp.backend.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoUserDTO{
        public Long id;
        public String name;
}