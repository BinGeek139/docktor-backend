package com.techasians.doctor.common.validator.group;

public class RegexContant {

    public static final String USERNAME_REGEX = "[a-zA-Z0-9]";
    public static final String STUDENT_CODE_REGEX = "^(B)(\\d\\d)([A-Z]\\w\\w\\w)(\\d\\d\\d)";
    public static final String NUMBER_CODE_REGEX = "^[0-9]$";
    public static final String ROOM_CODE_REGEX = "^(PTIT_)(\\d\\d\\d)$";
    public static final String ROOM_TYPE_REGEX = "(DOUBLE)|(SINGLE)|(GROUP)";
    public static final String SERVICE_CODE_REGEX = "^(SER_)(\\d\\d)$";
    public static final String SERVICE_NAME_REGEX = "[a-zA-Z]";
    public static final String CARD_CODE_REGEX = "\\b\\d{1,12}\\b";
    public static final String GRADE_CODE_REGEX = "^(D)(\\d\\d)(\\w\\w\\w\\w)(\\d\\d)";
    public static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@\" + \"[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
    public static final String DATE_OF_BIRTH = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String DATETIME = "\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])*";
    public static final String ROLE = "^(ROLE_)";
}
