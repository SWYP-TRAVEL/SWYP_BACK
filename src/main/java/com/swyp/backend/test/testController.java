package com.swyp.backend.test;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vi/example")
@RequiredArgsConstructor
@Tag(name="TEST API", description = "test api 확인")
public class testController {
    @GetMapping("/test")
    public void test(){

    }

}
