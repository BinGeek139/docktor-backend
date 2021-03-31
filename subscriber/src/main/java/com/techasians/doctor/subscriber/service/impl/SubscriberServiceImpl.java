package com.techasians.doctor.subscriber.service.impl;

import com.restfb.types.User;
import com.techasians.doctor.subscriber.domain.Subscriber;
import com.techasians.doctor.subscriber.exception.CodeUnConfirmException;
import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.repository.SubscriberRepository;
import com.techasians.doctor.subscriber.security.AuthoritiesConstants;
import com.techasians.doctor.subscriber.security.jwt.TokenProvider;
import com.techasians.doctor.subscriber.service.EmailService;
import com.techasians.doctor.subscriber.service.SMSService;
import com.techasians.doctor.subscriber.service.SubscriberService;
import com.techasians.doctor.subscriber.service.mapper.BaseMapper;
import com.techasians.doctor.subscriber.utils.*;
import com.techasians.doctor.subscriber.web.rest.api.form.*;
import com.techasians.doctor.subscriber.web.rest.api.view.LoginView;
import com.techasians.doctor.subscriber.web.rest.api.view.RegisterView;
import com.techasians.doctor.subscriber.web.rest.errors.Google0AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

/**
 * SubscriberServiceImpl class.
 *
 * @author ngocquang
 * @since 20/1/2020
 */

@Service
public class SubscriberServiceImpl implements SubscriberService {
    BaseMapper<Subscriber, RegisterForm> registerFormMapper = new BaseMapper<>(Subscriber.class, RegisterForm.class);
    BaseMapper<Subscriber, RegisterView> registerViewMapper = new BaseMapper<>(Subscriber.class, RegisterView.class);

    AuthenticationManager authenticationManager;
    TokenProvider tokenProvider;

    public SubscriberServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Value("${subscriber.expire_token_reset_password}")
    private long expireTokenResetPassword;

    @Value("${subscriber.expire_date_password}")
    private long expireDatePassword;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SMSService smsService;

    @Value("subscriber.sms.content")
    String content;
    private final String PHONE_NUMBER="phoneNumber";
    private final String PASSWORD="password";
    private final String CODE="code";
    @Override
    public RegisterView register(RegisterForm registerForm) throws ValidateException {
        String password = registerForm.getPassword().trim();
        String phoneNumber = registerForm.getPhoneNumber().trim();

        if (Strings.isEmpty(phoneNumber)) {
            throw new ValidateException(PHONE_NUMBER,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }


        if (Strings.isEmpty(password)) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }
        if (Common.lengthOfString(password) <= Constants.PASSWORD_MIN_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_SHORT);
        }

        if (Common.lengthOfString(password) > Constants.PASSWORD_MAX_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_LONG);
        }
        if(!Pattern.matches(Constants.REGEX_PASSWORD_REGISTER,password)){
            throw new ValidateException(PASSWORD,Constants.MESSAGE_INVALID_PASSWORD);
        }




        // Check the existence of phone numbers
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findFirstByPhoneNumber(phoneNumber);
        Subscriber subscriberCheck = null;
        if (optionalSubscriber.isPresent()) {
            subscriberCheck = optionalSubscriber.get();

            /**
             * neu so dien thoai da duoc xac thuc, thi throw exception.
             * neu so dien thoai chua duoc xac thuc, thi thi tiep tuc.
             */
            if (!Constants.Flag.PENDING_CONFIRM.equals(subscriberCheck.getFlag())) {
                throw new ValidateException(PHONE_NUMBER,Constants.MESSAGE_PHONE_NUMBER_ALREADY_EXIST);
            }

        }

        // endCode password
        password = passwordEncoder.encode(password);

        registerForm.setPassword(password);
        registerForm.setPhoneNumber(phoneNumber);


        // encapsulation subscriber
        Subscriber subscriber = registerFormMapper.toPersistenceBean(registerForm);
        if (!Objects.isNull(subscriberCheck)) {
            subscriber.setId(subscriberCheck.getId());
        }

        subscriber.setStatus(Constants.NOT_ACTIVE);
        subscriber.setRole(AuthoritiesConstants.PATIENT);
        subscriber.setPassword(registerForm.getPassword());
        subscriber.setFlag(Constants.Flag.NONE);

        // create code OTP
        String otp = Common.generateNumber(Constants.LENGTH_CODE_CONFIRM);
        subscriber.setOtp(otp);
        subscriber.setFlag(Constants.Flag.PENDING_CONFIRM);
        long timeExpire = System.currentTimeMillis() + expireTokenResetPassword;
        subscriber.setExpireOTPDate(new Timestamp(timeExpire));

        // save subscriber
        subscriber = subscriberRepository.save(subscriber);

        String contentSend = String.format(content, otp);
        Boolean check = smsService.sendSMS(registerForm.getPhoneNumber(), otp);
        if (!check) {
            throw new ValidateException(Constants.MESSAGE_NOT_SEND_SMS);
        }
        RegisterView registerView = registerViewMapper.toDtoBean(subscriber);

        return registerView;
    }

    public void validateRegister(RegisterForm registerForm) {
        String password = registerForm.getPassword().trim();
        String phoneNumber = registerForm.getPhoneNumber().trim();

        // Check the existence of phone numbers
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findFirstByPhoneNumber(phoneNumber);
        if (optionalSubscriber.isPresent()) {
            throw new ValidateException(Constants.MESSAGE_PHONE_NUMBER_ALREADY_EXIST);
        }

        // endCode password
        password = passwordEncoder.encode(password);

        registerForm.setPassword(password);
        registerForm.setPhoneNumber(phoneNumber);
    }

    @Override
    public LoginView login(LoginForm loginForm) {
        validateLogin(loginForm);

        // Authenticate login information
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginForm.getPhoneNumber(),
                loginForm.getPassword())
        );


        String token = tokenProvider.createToken(authentication, false);

        Subscriber subscriber = findSubscriberByPhoneNumber(loginForm.getPhoneNumber());
        subscriber.setToken(token);
        subscriberRepository.save(subscriber);

        LoginView loginView = new LoginView();
        loginView.setToken(token);
        return loginView;
    }

    public void validateLogin(LoginForm loginForm) {
        loginForm.setPassword(loginForm.getPassword().trim());
        loginForm.setPhoneNumber(loginForm.getPhoneNumber().trim());
        String phoneNumber=loginForm.getPhoneNumber();
        String password=loginForm.getPassword();

        if (Strings.isEmpty(phoneNumber)) {
            throw new ValidateException(PHONE_NUMBER,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }

        if (Strings.isEmpty(password)) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }
        if (Common.lengthOfString(password) <= Constants.PASSWORD_MIN_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_SHORT);
        }

        if (Common.lengthOfString(password) > Constants.PASSWORD_MAX_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_LONG);
        }
        if(!Pattern.matches(Constants.REGEX_PASSWORD_REGISTER,password)){
            throw new ValidateException(PASSWORD,Constants.MESSAGE_INVALID_PASSWORD);
        }
    }

    @Autowired
    EmailService emailService;

    @Override
    public void confirmBySMS(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        Subscriber subscriber = findSubscriberByPhoneNumber(phoneNumber);
//        String mail = subscriber.getEmail();

//        if (Objects.isNull(mail)) {
//            throw new ResourceNotFoundException(Constants.MESSAGE_MAIL_NOT_EXIST);
//        }

        String code = Common.generateNumber(Constants.LENGTH_CODE_CONFIRM);
        subscriber.setOtp(code);
        long timeExpire = System.currentTimeMillis() + expireTokenResetPassword;
        subscriber.setExpireOTPDate(new Timestamp(timeExpire));
        subscriber.setFlag(Constants.Flag.PENDING_CONFIRM);
        subscriberRepository.save(subscriber);

        String contentSend = String.format(content, code);
        Boolean check = smsService.sendSMS(phoneNumber, contentSend);
        if (!check) {
            throw new ValidateException(Constants.MESSAGE_NOT_SEND_SMS);
        }
    }

    @Override
    public void confirmCode(ConfirmCodeForm confirmCodeForm, Boolean isResetPass) {
        validateConfirmCode(confirmCodeForm);

        Subscriber subscriber = findSubscriberByPhoneNumber(confirmCodeForm.getPhoneNumber());

        long now = System.currentTimeMillis();
        long expireTime = subscriber.getExpireOTPDate().getTime();
        if (now > expireTime) {
            throw new ValidateException(CODE,Constants.MESSAGE_EXPIRE_CODE);
        }

        String code = subscriber.getOtp();
        if (Objects.isNull(code) || !code.equals(confirmCodeForm.getCode())) {
            throw new ValidateException(CODE,Constants.MESSAGE_CODE_CONFIRM_INVALID);
        }

        if (isResetPass) {
            subscriber.setFlag(Constants.Flag.CONFIRMED);
        } else {
            subscriber.setFlag(Constants.Flag.NONE);
            subscriber.setStatus(Constants.ACTIVE);
        }
        subscriberRepository.save(subscriber);
    }

    void validateConfirmCode(ConfirmCodeForm confirmCodeForm) {
        confirmCodeForm.setCode(confirmCodeForm.getCode().trim());
        confirmCodeForm.setPhoneNumber(confirmCodeForm.getPhoneNumber().trim());
    }

    @Override
    public void forgetPassword(ForgetPasswordForm forgetPasswordForm) {
        validateForgetPassword(forgetPasswordForm);
        Subscriber subscriber = findSubscriberByPhoneNumber(forgetPasswordForm.getPhoneNumber());

        if (!Constants.Flag.CONFIRMED.equals(subscriber.getFlag())) {
            throw new CodeUnConfirmException(Constants.MESSAGE_CODE_UN_CONFIRM);
        }

        // endCode password
        String password = passwordEncoder.encode(forgetPasswordForm.getPassword());

        // encapsulation subscriber
        subscriber.setPassword(password);
        subscriber.setFlag(Constants.Flag.NONE);
        subscriberRepository.save(subscriber);

    }

    public void validateForgetPassword(ForgetPasswordForm forgetPasswordForm) {
        forgetPasswordForm.setPassword(forgetPasswordForm.getPassword().trim());
        forgetPasswordForm.setPhoneNumber(forgetPasswordForm.getPhoneNumber().trim());
        String phoneNumber=forgetPasswordForm.getPhoneNumber();
        String password=forgetPasswordForm.getPassword();

        if (Strings.isEmpty(phoneNumber)) {
            throw new ValidateException(PHONE_NUMBER,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }

        if (Strings.isEmpty(password)) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE_PHONE_NUMBER_NOT_BLANK);
        }
        if (Common.lengthOfString(password) <= Constants.PASSWORD_MIN_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_SHORT);
        }

        if (Common.lengthOfString(password) > Constants.PASSWORD_MAX_LENGTH) {
            throw new ValidateException(PASSWORD,Constants.MESSAGE.PASSWORD_TOO_LONG);
        }
        if(!Pattern.matches(Constants.REGEX_PASSWORD_REGISTER,password)){
            throw new ValidateException(PASSWORD,Constants.MESSAGE_INVALID_PASSWORD);
        }
    }

    @Autowired
    private RestFB restFb;

    @Override
    public LoginView checkLoginFacebook(FacebookLoginForm facebookLoginForm) {
        User user = restFb.getUserInfo(facebookLoginForm.getAccessToken());
        UserDetails userDetail = restFb.buildUser(user);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        Authentication authentication = new AnonymousAuthenticationToken(user.getId(), userDetail, grantedAuthorities);
        String token = tokenProvider.createToken(authentication, false);

        Optional<Subscriber> optionalSubscriber = subscriberRepository.findFirstByIdFacebook(user.getId());
        if (optionalSubscriber.isPresent()) {
            Subscriber subscriber = optionalSubscriber.get();
            subscriber.setToken(token);
            subscriberRepository.save(subscriber);
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setStatus(Constants.ACTIVE);
        subscriber.setToken(token);
        subscriber.setEmail(user.getEmail());
        subscriber.setIdFacebook(user.getId());
        subscriberRepository.save(subscriber);

        return new LoginView(token);
    }

    @Autowired
    private GoogleUtils googleUtils;

    @Override
    public LoginView checkLoginGoogle(LoginGoogleForm loginGoogleForm) throws Google0AuthException {
        GooglePojo googlePojo = null;
        try {
            googleUtils.getUserInfo(loginGoogleForm.getAccessToken());
        } catch (IOException e) {
            throw new Google0AuthException();
        }

        UserDetails userDetail = googleUtils.buildUser(googlePojo);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        Authentication authentication = new AnonymousAuthenticationToken(googlePojo.getId(), userDetail, grantedAuthorities);
        String token = tokenProvider.createToken(authentication, false);

        Optional<Subscriber> optionalSubscriber = subscriberRepository.findFirstByIdGoogle(googlePojo.getId());
        if (optionalSubscriber.isPresent()) {
            Subscriber subscriber = optionalSubscriber.get();
            subscriber.setToken(token);
            subscriberRepository.save(subscriber);
        }

        Subscriber subscriber = new Subscriber();
        subscriber.setStatus(Constants.ACTIVE);
        subscriber.setToken(token);
        subscriber.setEmail(googlePojo.getEmail());
        subscriber.setIdGoogle(googlePojo.getId());
        subscriberRepository.save(subscriber);

        return new LoginView(token);
    }

    public Subscriber findSubscriberByPhoneNumber(String phoneNumber) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findFirstByPhoneNumber(phoneNumber);
        if (!optionalSubscriber.isPresent()) {
            throw new ValidateException(Constants.MESSAGE_PHONE_NUMBER_NOT_EXIST);
        }
        return optionalSubscriber.get();
    }

}
