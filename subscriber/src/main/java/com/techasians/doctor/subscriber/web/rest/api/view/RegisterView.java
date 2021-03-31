package com.techasians.doctor.subscriber.web.rest.api.view;

import java.io.Serializable;

/**
 * RegisterView class.
 * Contains the data used to return to the client when successful registration.
 *
 * @author ngocquang
 * @since 20/1/2020
 */

public class RegisterView implements Serializable {

    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
