package com.swyp.backend.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(title = "SWYP API Docs",
                description = "SWYP 백엔드 API 문서")
)
@Configuration
public class SwaggerConfig {

}
