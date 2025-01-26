package com.security.springapp.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CronService {
    @Autowired
    RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CronService.class);

    @Value("${BACKEND_URL}")
    public String BASE_URL;

    @Scheduled(fixedRate = 890000)
    public void pingSelf() {
        logger.info("Attempting to ping self at: {}", LocalDateTime.now());
        try {
            String url = BASE_URL + "/api/health";
            String response = restTemplate.getForObject(url, String.class);
            logger.info("Ping successful: {}", response);
        } catch (RestClientException e) {
            logger.error("Failed to ping self: {}", e.getMessage());
        }
    }
}
