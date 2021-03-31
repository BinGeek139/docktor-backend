package com.techasians.doctor.common.validator;

import java.util.Arrays;
import java.util.List;

public class ValidatorMessage {

    String key;

    List<Object> params;

    public ValidatorMessage(){

    }

    public ValidatorMessage(String key, Object... params) {
        this.key = key;
        this.params = Arrays.asList(params);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ValidatorMessage{" +
                "key='" + key + '\'' +
                ", params=" + params +
                '}';
    }
}
