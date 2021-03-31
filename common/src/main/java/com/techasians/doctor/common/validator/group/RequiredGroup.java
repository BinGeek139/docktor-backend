package com.techasians.doctor.common.validator.group;

public class RequiredGroup  implements BaseGroup {
    boolean isRequired = false;

    public RequiredGroup() {

    }

    public static RequiredGroup ofTrue() {
        return new RequiredGroup(true);
    }

    public RequiredGroup(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

}
