package com.swyp.backend.itinerary.route.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.swyp.backend.itinerary.route.dto.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapApiService {//webflux 로?

    private final String tmapApiKey;
    private final String tmapApiWalkingUrl;
    private final String tmapApiDrivingUrl;
    private final String kakaoCoordinatesUrl;
    private final String kakaoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public MapApiService(
            @Value("${tmap.api.key}") String tmapApiKey
            , @Value("${tmap.api.url.walking}") String tmapApiWalkingUrl
            , @Value("${tmap.api.url.driving}") String tmapApiDrivingUrl
            , @Value("${kakao.map.coordinates.url}") String kakaoCoordinatesUrl
            , @Value("${kakao.client.id}") String kakaoApiKey) {
        this.tmapApiKey = tmapApiKey;
        this.tmapApiWalkingUrl = tmapApiWalkingUrl;
        this.tmapApiDrivingUrl = tmapApiDrivingUrl;
        this.kakaoCoordinatesUrl = kakaoCoordinatesUrl;
        this.kakaoApiKey = kakaoApiKey;
    }


    public Integer getWalkingTime(String start, String end){
        return getWalkingTime(getCoordinates(start), getCoordinates(end));
    }

    public Integer getDrivingTime(String start, String end){
        return getDrivingTime(getCoordinates(start), getCoordinates(end));
    }

    public Integer getWalkingTime(Coordinates start, Coordinates end){
        return sendTmapRequest(tmapApiWalkingUrl, start, end);
    }

    public Integer getDrivingTime(Coordinates start, Coordinates end){
        return sendTmapRequest(tmapApiDrivingUrl, start, end);
    }

    public Coordinates getCoordinates(String address){ //코드 별로
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+kakaoApiKey);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("query", address);


        HttpEntity<String> entity = new HttpEntity<>(headers);
        JsonNode response = restTemplate.exchange(
                kakaoCoordinatesUrl,
                HttpMethod.GET,
                entity,
                JsonNode.class,
                uriVariables
        ).getBody();

        double latitude = response.get("documents").get(0).get("x").asDouble();
        double longitude = response.get("documents").get(0).get("y").asDouble();

        return new Coordinates(latitude, longitude);
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
        headers.set("appKey", tmapApiKey);
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
