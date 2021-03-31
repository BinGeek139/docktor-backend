package com.techasians.doctor.subscriber.service;

public interface SMSService {
    Boolean sendSMS(String phone,String content);
}
