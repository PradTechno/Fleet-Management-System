package com.example.fleetmanagementsystem.service;

import com.example.fleetmanagementsystem.domain.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RouteApiService {
    private final RestTemplate restTemplate;
    @Autowired
    private Environment environment;
    private String url = "http://dev.virtualearth.net/REST/v1/Routes?wayPoint.1={origin}&waypoint.2={destination}&maxSolutions=1&key={key}";

    public RouteApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public Route calculateRoute(String origin, String destination) throws JsonProcessingException {
        var response = this.restTemplate.getForObject(url, String.class, origin, destination, environment.getProperty("routeApiKey"));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        Long distance = Math.round(rootNode.get("resourceSets").get(0).get("resources").get(0).get("travelDistance").asDouble());
        Long duration = Math.round(rootNode.get("resourceSets").get(0).get("resources").get(0).get("travelDuration").asDouble() / 60);
        Route route = new Route(distance, duration);
        return route;
    }
}
