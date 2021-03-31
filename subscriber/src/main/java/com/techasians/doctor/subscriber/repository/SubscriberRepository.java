package com.techasians.doctor.subscriber.repository;

import com.techasians.doctor.subscriber.domain.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * SubscriberRepository class.
 *
 * @author ngocquang
 * @since 20/1/2020
 */


public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findFirstByPhoneNumber(String phoneNumber);
    Optional<Subscriber> findFirstByIdFacebook(String idFacebook);
    Optional<Subscriber> findFirstByIdGoogle(String idGoogle);
    Optional<Subscriber> findFirstByToken(String token);


}
