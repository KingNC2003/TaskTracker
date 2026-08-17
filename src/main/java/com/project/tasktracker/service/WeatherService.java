package com.project.tasktracker.service;

import com.project.tasktracker.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WeatherService {

    private static final Logger logger =
            LoggerFactory.getLogger(WeatherService.class);

    private final RestClient restClient = RestClient.create();

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.latitude}")
    private double latitude;

    @Value("${weather.longitude}")
    private double longitude;

    public WeatherResponse getCurrentWeather() {

        try {

            return restClient
                    .get()
                    .uri(
                            "https://api.openweathermap.org/data/2.5/weather"
                                    + "?lat={lat}"
                                    + "&lon={lon}"
                                    + "&appid={key}"
                                    + "&units=imperial",
                            latitude,
                            longitude,
                            apiKey
                    )
                    .retrieve()
                    .body(WeatherResponse.class);

        } catch (Exception exception) {

            logger.error(
                    "Weather API request failed: {}",
                    exception.getMessage()
            );

            return null;
        }
    }
}