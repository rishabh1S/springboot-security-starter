package com.security.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.springapp.service.EmailService;

/**
 * TestController
 */
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/health")
    public String healthCheck() {
        return "The app is running successfully.";
    }

    @PostMapping("/test-email")
    public ResponseEntity<?> sendTestEmail() {
        emailService.sendEmail("siddharthkonnar@gmail.com", "Test Email",
                "This is a test email from Spring Boot!");
        return ResponseEntity.ok("Email sent successfully!");
    }
}