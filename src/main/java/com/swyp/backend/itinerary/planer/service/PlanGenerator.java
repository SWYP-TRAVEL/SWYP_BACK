package com.swyp.backend.itinerary.planer.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanGenerator {
    String mcpClientUrl = "";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Void> generateRecommendDestinations(Object userInput){
        String path = "/triplet";

        Map<String, Object> uriVariables = new HashMap<>();
        uriVariables.put("itinerary_details", userInput);

        JsonNode response = restTemplate.getForObject(
                path,
                JsonNode.class,
                uriVariables
        );
        return null;
    }
}
