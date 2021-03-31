package com.techasians.doctor.subscriber.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);

}
