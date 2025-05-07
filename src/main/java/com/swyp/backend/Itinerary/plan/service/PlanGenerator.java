package com.swyp.backend.itinerary.plan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swyp.backend.itinerary.plan.dto.PlanPreviewDto;
import com.swyp.backend.itinerary.plan.dto.UserPlanInputDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PlanGenerator {
    private final String mcpClientHost;
    private final int mcpClientPort;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper jacksonObjectMapper;

    public PlanGenerator(@Value("${mcp.config.client.host}") String mcpClientHost,
                         @Value("${mcp.config.client.port}") int mcpClientPort, ObjectMapper jacksonObjectMapper) {
        this.mcpClientHost = mcpClientHost;
        this.mcpClientPort = mcpClientPort;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public List<PlanPreviewDto> generateRecommendDestinations(UserPlanInputDto input) {
        String path = "http://"+mcpClientHost+":"+mcpClientPort+"/api/triplet";
        System.out.println(path);
        Map<String, Object> requestParams = new HashMap<>();
        requestParams.put("travel_with", input.getTravelWith());
        requestParams.put("start_date", input.getStartDate().toString());
        requestParams.put("end_date", input.getEndDate().toString());
        requestParams.put("description", input.getDescription());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestParams, headers);

        String response = restTemplate.postForObject(path, entity, JsonNode.class).asText(); //이유는 모르겠는데 양쪽 끝에 " 가 추가돼서 한번에 파싱이 안됨
        JsonNode jsonNode = null;
        try {
            jsonNode = jacksonObjectMapper.readTree(response);
        }
        catch (JsonProcessingException e) {
            return null;
        }
        

        System.out.println("qqqqqqqqq"+response.toString());
        List<PlanPreviewDto> dtos = new ArrayList<>();

        jsonNode.forEach(tripNode -> {
            PlanPreviewDto dto = PlanPreviewDto.builder()
                    .name(tripNode.get("area_name").asText())
                    .theme(tripNode.get("area_travel_theme").asText())
                    .imageUrl(tripNode.get("area_cover_image").asText())
                    .build();
            dtos.add(dto);
        });

        return dtos;
    }
}