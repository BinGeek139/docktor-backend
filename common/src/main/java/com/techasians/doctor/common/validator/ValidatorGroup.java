package com.techasians.doctor.common.validator;


import com.techasians.doctor.common.validator.group.*;

import java.util.ArrayList;
import java.util.List;

public class ValidatorGroup {

    String fieldName;
    Object value;
    List<ValidateObject> groups = new ArrayList<>();

    public ValidatorGroup() {

    }

    public ValidatorGroup ofFieldName(String fieldName) {
        setFieldName(fieldName);
        return this;
    }

    public ValidatorGroup ofValue(Object value) {
        setValue(value);
        return this;
    }

    public ValidatorGroup ofMinLength(long min) {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new MinLengthGroup().ofMin(min));
        groups.add(object);

        return this;
    }


    public ValidatorGroup ofMaxLength(long max) {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new MaxLengthGroup().ofMax(max));
        groups.add(object);

        return this;
    }

    public ValidatorGroup ofMinValue(double minValue) {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new MinValueGroup().ofMinValue(minValue));
        groups.add(object);

        return this;
    }

    public ValidatorGroup ofMaxValue(double maxValue) {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new MaxValueGroup().ofMaxValue(maxValue));
        groups.add(object);

        return this;
    }

    public ValidatorGroup ofRegex(String regex) {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new RegexGroup().ofRegex(regex));
        groups.add(object);

        return this;
    }

    public ValidatorGroup ofRequired() {
        ValidateObject object = new ValidateObject();
        object.setFieldName(this.fieldName);
        object.setValue(this.value);
        object.setValidatorGroup(new RequiredGroup().ofTrue());
        groups.add(object);
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

    public List<ValidateObject> getGroups() {
        return groups;
    }

    public void setGroups(List<ValidateObject> groups) {
        this.groups = groups;
    }
}
