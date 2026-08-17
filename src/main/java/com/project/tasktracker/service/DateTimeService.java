package com.project.tasktracker.service;

import com.project.tasktracker.model.TimeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DateTimeService {

    private static final Logger logger =
            LoggerFactory.getLogger(DateTimeService.class);
    private final RestClient restClient =
            RestClient.create();
    public String getCurrentDateTime() {
        try {
            TimeResponse response = restClient
                    .get()
                    .uri(
                        "https://timeapi.io/api/Time/current/zone?timeZone=UTC"
                    )
                    .retrieve()
                    .body(TimeResponse.class);
            if (response == null) {
                return "Time unavailable";
            }
            return response.getDateTime();
        } catch (Exception exception) {
            logger.error(
                    "Date/time API request failed: {}",
                    exception.getMessage()
            );
            return "Time unavailable";
        }
    }
}