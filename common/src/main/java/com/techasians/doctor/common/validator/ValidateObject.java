package com.techasians.doctor.common.validator;


import com.techasians.doctor.common.validator.group.BaseGroup;

public class ValidateObject {
    Object value;
    String fieldName;
    BaseGroup validatorGroup;

    public ValidateObject() {

    }
    public ValidateObject(Object value, String fieldName, BaseGroup validatorGroup) {
        this.value = value;
        this.fieldName = fieldName;
        this.validatorGroup = validatorGroup;
    }

    public ValidateObject(Object value, BaseGroup validatorGroup) {
        this.value = value;
        this.validatorGroup = validatorGroup;
    }

    public ValidateObject ofValue(Object value){
        setValue(value);
        return this;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public BaseGroup getValidatorGroup() {
        return validatorGroup;
    }

    public void setValidatorGroup(BaseGroup validatorGroup) {
        this.validatorGroup = validatorGroup;
    }
}
