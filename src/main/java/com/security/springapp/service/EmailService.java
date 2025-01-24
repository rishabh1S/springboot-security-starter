package com.security.springapp.service;

public interface EmailService {
    public void sendEmail(String to, String subject, String text);
}
