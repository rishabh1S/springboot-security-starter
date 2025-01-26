package com.security.springapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class CronService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${BACKEND_URL}")
    public static String BASE_URL;

    @Scheduled(fixedRate = 890000)
    public void pingSelf() {
        try {
            String url = BASE_URL + "/api/health";
            restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            System.out.println("Failed to ping: " + e.getMessage());
        }
    }
}
