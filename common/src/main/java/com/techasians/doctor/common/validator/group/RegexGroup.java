package com.techasians.doctor.common.validator.group;

public class RegexGroup  implements BaseGroup {

    String regex;

    public RegexGroup() {

    }

    public static RegexGroup ofRegex(String regex) {
        RegexGroup regexGroup = new RegexGroup(regex);
        return regexGroup;
    }

    public RegexGroup(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }


}
