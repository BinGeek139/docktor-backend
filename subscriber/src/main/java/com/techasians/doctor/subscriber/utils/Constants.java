package com.techasians.doctor.subscriber.utils;

public interface Constants {
    String COMMON_MESSAGE_FAIL = "common.message.fail";
    int PASSWORD_MAX_LENGTH = 255;
    int PASSWORD_MIN_LENGTH = 6;

    interface MESSAGE{
        String PASSWORD_TOO_SHORT="password.tooShort";
        String PASSWORD_TOO_LONG="password.tooLong";
        String OTP="otp.invalid";
        String VALIDATE_FAILURE="validate.failure";
    }
    interface PATTERN{
        String OPT="\\d{6,6}";
    }


    String MESSAGE_NOT_NULL = "not.null";
    String MESSAGE_NOT_BLANK = "not.blank";
    String MESSAGE_PHONE_NUMBER_NOT_NULL = "phoneNumber.not.null";
    String MESSAGE_PHONE_NUMBER_NOT_BLANK = "phoneNumber.not.blank";
    String REGEX_PHONE_NUMBER = "^\\s*(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})\\s*$";
    String REGEX_CODE_CONFIRM="([0-9]){6}";
    String MESSAGE_CODE_CONFIRM_INVALID="code.confirm.invalid";
    String MESSAGE_EXPIRE_CODE="code.confirm.expire";
    String MESSAGE_INVALID_PHONE_NUMBER = "phoneNumber.invalid";
    String MESSAGE_INVALID_PASSWORD = "password.invalid";
//    String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,255}$";
    String REGEX_PASSWORD_REGISTER = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{0,}$";

    Integer ACTIVE = 1;
    Integer NOT_ACTIVE = 0;
    String SUCCESS = "success";
    String MESSAGE_MAIL_NOT_EXIST="message.mail.not.exist";
    String MESSAGE_CODE_UN_CONFIRM="message.code.un.confirm";
    String MESSAGE_NOT_SEND_SMS="message.not.send.sms";
    interface LANGUAGE {
        String VI = "vi";
        String EN = "en";
    }
    String MESSAGE_PHONE_NUMBER_ALREADY_EXIST = "phone.number.already.exist";
    String MESSAGE_PHONE_NUMBER_NOT_EXIST = "phone.number.not.exist";
    String MESSAGE_SYSTEM_ERROR = "system.error";
    String MESSAGE_LOGIN_FAILURE = "login.failure";
    String MESSAGE_LOGIN_USER_DISABLE = "login.user.disable";
    String MESSAGE_LOGIN_TOKEN_EXPIRED="login.token.expired";
    String MESSAGE_INVALID_ACCESS_TOKEN_FACEBOOK="login.invalid.access.token.facebook";
    String MESSAGE_INVALID_ACCESS_TOKEN_GOOGLE="login.invalid.access.token.google";
    String MESSAGE_DUPLICATE_TOKEN="login.duplicate.access.token";
    int LENGTH_CODE_CONFIRM=6;
    String SUBJECT_EMAIL_CONFIRM="EMAIL CONFIRM";

    interface Flag {
        Integer NONE=0;
        Integer PENDING_CONFIRM=1;
        Integer CONFIRMED=2;
    }


}
