package com.techasians.doctor.subscriber.service;

import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.web.rest.api.form.*;
import com.techasians.doctor.subscriber.web.rest.api.view.LoginView;
import com.techasians.doctor.subscriber.web.rest.api.view.RegisterView;
import com.techasians.doctor.subscriber.web.rest.errors.Google0AuthException;

import java.io.IOException;

/**
 * SubscriberService interface.
 *
 * @author ngocquang
 * @since 20/1/2020
 */
public interface SubscriberService {
    RegisterView register(RegisterForm registerForm) throws ValidateException;

    LoginView login(LoginForm loginForm);

    void confirmBySMS(String phoneNumber);

    void confirmCode(ConfirmCodeForm confirmCodeForm, Boolean isResetPass);

    void forgetPassword(ForgetPasswordForm forgetPasswordForm);

    LoginView checkLoginFacebook(FacebookLoginForm facebookLoginForm);

    LoginView checkLoginGoogle(LoginGoogleForm loginGoogleForm) throws Google0AuthException;

}
