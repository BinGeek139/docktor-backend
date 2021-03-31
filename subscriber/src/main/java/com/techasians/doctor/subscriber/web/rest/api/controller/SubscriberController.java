package com.techasians.doctor.subscriber.web.rest.api.controller;

import com.restfb.exception.FacebookOAuthException;
import com.techasians.doctor.common.respond.ResponseData;
import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.service.SubscriberService;
import com.techasians.doctor.subscriber.utils.BaseComponent;
import com.techasians.doctor.subscriber.utils.Constants;
import com.techasians.doctor.subscriber.utils.RestFB;
import com.techasians.doctor.subscriber.web.rest.api.form.*;
import com.techasians.doctor.subscriber.web.rest.api.view.LoginView;
import com.techasians.doctor.subscriber.web.rest.api.view.RegisterView;
import com.techasians.doctor.subscriber.web.rest.errors.Google0AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * SubscriberController class
 * REST controller for managing the subscriber
 *
 * @author ngocquang
 * @since 20/1/2020
 */

@RestController
@RequestMapping("/api/v1/subscriber")
public class SubscriberController extends BaseComponent {
    @Autowired
    SubscriberService subscriberService;

    /**
     * POST  /register : register the account.
     * Creates a new user if the login and email are not already used.
     *
     * @param registerForm
     * @return the ResponseEntity with status 200 (OK) and body is {@link ResponseData} if register success.
     * @throws ValidateException if invalid data.
     */
    @PostMapping("register")
    public ResponseEntity<ResponseData> register(@Valid @RequestBody RegisterForm registerForm) throws ValidateException {
        RegisterView registerView = subscriberService.register(registerForm);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS), registerView);
        return ResponseEntity.ok(responseData);
    }
    /**
     * POST /confirm-account-code:  API used for OTP authentication when registration.
     *
     * @param confirmCodeForm
     * @return
     */
    @PostMapping("confirm-code-otp-register")
    public ResponseEntity<ResponseData> confirmAccountOtp(@Valid @RequestBody ConfirmCodeForm confirmCodeForm) {
        subscriberService.confirmCode(confirmCodeForm, false);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS));
        return ResponseEntity.ok(responseData);
    }

    /**
     * POST /login
     *
     * @param loginForm
     * @return the ResponseEntity with status 200 (OK) and body is ResponseData if login success.
     */
    @PostMapping("login")
    public ResponseEntity<ResponseData> login(@Valid @RequestBody LoginForm loginForm) {
        LoginView loginView = subscriberService.login(loginForm);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS), loginView);
        return ResponseEntity.ok(responseData);
    }

    /**
     * POST /request-confirm : Request sending OTP code.
     * @param requestConfirmForm
     * @return
     */
    @PostMapping("request-send-otp")
    public ResponseEntity<ResponseData> confirmOTP(@RequestBody @Valid RequestConfirmForm requestConfirmForm) {
        subscriberService.confirmBySMS(requestConfirmForm.getPhoneNumber());
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS));
        return ResponseEntity.ok(responseData);
    }

    /**
     * POST /confirm-code-reset-pass :  API used for OTP authentication when change password.
     *
     * @param confirmCodeForm
     * @return
     */
    @PostMapping("confirm-code-reset-pass")
    public ResponseEntity<ResponseData> confirmCodeResetPass(@Valid @RequestBody ConfirmCodeForm confirmCodeForm) {
        subscriberService.confirmCode(confirmCodeForm, true);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS));
        return ResponseEntity.ok(responseData);
    }

    /**
     * POST /forget-password : API used to change password.
     *
     * @param forgetPasswordForm
     * @return
     */
    @PostMapping("forget-password")
    public ResponseEntity<ResponseData> forgetPassword(ForgetPasswordForm forgetPasswordForm) {
        subscriberService.forgetPassword(forgetPasswordForm);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS));
        return ResponseEntity.ok(responseData);
    }



    @Autowired
    private RestFB restFb;

    /**
     *
     * @param facebookLoginForm
     * @return
     * @throws FacebookOAuthException
     */
    @PostMapping("login-facebook")
    public ResponseEntity<ResponseData> loginFacebook(FacebookLoginForm facebookLoginForm)
        throws FacebookOAuthException {

        LoginView loginView=subscriberService.checkLoginFacebook(facebookLoginForm);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS),loginView);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("login-google")
    public ResponseEntity<ResponseData> loginGoogle(LoginGoogleForm googleForm)
        throws Google0AuthException {

        LoginView loginView=subscriberService.checkLoginGoogle(googleForm);
        ResponseData responseData = ResponseData.ofSuccess(translate(Constants.SUCCESS));
        return ResponseEntity.ok(responseData);
    }

}
