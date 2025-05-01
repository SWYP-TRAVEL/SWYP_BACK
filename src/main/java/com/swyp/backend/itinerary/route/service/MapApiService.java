package com.swyp.backend.itinerary.route.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.swyp.backend.itinerary.route.dto.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapApiService {

    private final String apiKey;
    private final String apiWalkingUrl;
    private final String apiDrivingUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public MapApiService(
            @Value("${tmap.api.key}") String apiKey
            , @Value("${tmap.api.url.walking}") String apiWalkingUrl
            , @Value("${tmap.api.url.driving}") String apiDrivingUrl) {
        this.apiKey = apiKey;
        this.apiWalkingUrl = apiWalkingUrl;
        this.apiDrivingUrl = apiDrivingUrl;
    }


    public Integer getWalkingTime(Coordinates start, Coordinates end){
        return sendTmapRequest(apiWalkingUrl, start, end);
    }

    public Integer getDrivingTime(Coordinates start, Coordinates end){
        return sendTmapRequest(apiDrivingUrl, start, end);
    }

    public Coordinates getCoordinates(String address){
        return null;
    }

    private Integer sendTmapRequest(String url, Coordinates start, Coordinates end) {
        HttpHeaders headers = setHeader();
        String body = setBody(start, end);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        JsonNode response = restTemplate.postForObject(url, entity, JsonNode.class);

        return extractTotalTime(response);
    }

    private HttpHeaders setHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("appKey", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String setBody(Coordinates start, Coordinates end){
        return String.format("""
        {
          "startX": "%f",
          "startY": "%f",
          "endX": "%f",
          "endY": "%f",
          "reqCoordType": "WGS84GEO",
          "resCoordType": "WGS84GEO",
          "startName": "출발지",
          "endName": "도착지"
        }
        """, start.latitude(), start.longitude(), end.latitude(), end.longitude());
    }

    private Integer extractTotalTime(JsonNode response) {
        if(response == null || response.isNull()){
            return -1;
        }
        return response.get("features").get(0).get("properties").get("totalTime").asInt();
    }
}
