package com.techasians.doctor.subscriber.web.rest.api.form;

import com.techasians.doctor.subscriber.utils.Constants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * ConfirmCodeForm class.
 *
 * @author ngocquang
 * @since 20/1/2020
 */
public class ConfirmCodeForm {
    @NotNull(message = Constants.MESSAGE_PHONE_NUMBER_NOT_NULL)
    @NotBlank(message = Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK)
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = Constants.MESSAGE_INVALID_PHONE_NUMBER)
    private String phoneNumber;

    @NotNull(message = Constants.MESSAGE_NOT_NULL)
    @NotBlank(message = Constants.MESSAGE_NOT_BLANK)
    @Pattern(regexp = Constants.PATTERN.OPT,message = Constants.MESSAGE.OTP)
    String code;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
