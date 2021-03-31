package com.techasians.doctor.subscriber.web.rest.api.form;

import com.techasians.doctor.subscriber.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
/**
 * RegisterForm class.
 * Contains data used for api  ForgetPassword.
 *
 * @author ngocquang
 * @since 20/1/2020
 */
public class ForgetPasswordForm {
    @NotNull(message = Constants.MESSAGE_NOT_NULL)
    @NotBlank(message = Constants.MESSAGE_NOT_BLANK)
    @Pattern(regexp = Constants.REGEX_PHONE_NUMBER, message = Constants.MESSAGE_INVALID_PHONE_NUMBER)
    private String phoneNumber;

    @NotNull(message = Constants.MESSAGE_NOT_NULL)
    @NotBlank(message = Constants.MESSAGE_NOT_BLANK)
    private String password;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
