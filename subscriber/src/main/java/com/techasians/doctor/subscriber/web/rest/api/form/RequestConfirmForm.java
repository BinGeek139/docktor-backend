package com.techasians.doctor.subscriber.web.rest.api.form;

import com.techasians.doctor.subscriber.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RequestConfirmForm {
    @NotNull(message = Constants.MESSAGE_PHONE_NUMBER_NOT_NULL)
    @NotBlank(message = Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK)
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = Constants.MESSAGE_INVALID_PHONE_NUMBER)
    private String phoneNumber;
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
