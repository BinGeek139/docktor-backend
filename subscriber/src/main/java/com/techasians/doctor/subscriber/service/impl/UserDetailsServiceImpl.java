package com.techasians.doctor.subscriber.service.impl;

import com.techasians.doctor.subscriber.domain.CustomUserDetails;
import com.techasians.doctor.subscriber.domain.Subscriber;
import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.repository.SubscriberRepository;
import com.techasians.doctor.subscriber.utils.Constants;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SubscriberRepository subscriberRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Subscriber> optionalSubscriber= subscriberRepository.findFirstByPhoneNumber(s);
        if(!optionalSubscriber.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        User
        return new CustomUserDetails(optionalSubscriber.get());
    }
}
