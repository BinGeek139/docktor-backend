package com.techasians.doctor.common.utils;

public final class Constants {
    public static final String PATH_AVATAR = "";
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    public static final String VIET_NAM_PHONE_REGEX = "[0-9]{11}";

    public static final int ON = 1;
    public static final int OFF = 0;

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String NUMBER = "^[0-9]+([.][0-9]?)?$";
    public static final String COMPARE_OPERATOR = "^[<>=]{1,2}$";

    public static final String RETURN_CODE_SUCCESS = "200";
    public static final String RETURN_CODE_ERROR = "400";


    public static final class ERROR_CODE {

        public static final String SUCCESS = "0";
        public static final String FAIL = "1";
        public static final String VALIDATE_FAIL = "2";

        public static final String SYSTEM_ERROR = "-1";
    }

    public static final class STATUS {
        public static final Long ACTIVE = 1L;
        public static final Long INACTIVE = 0L;
    }

    public static class STATUS_COMMON {
        public static final Boolean RESPONSE_STATUS_TRUE = true;
        public static final Boolean RESPONSE_STATUS_FALSE = false;
    }
}
