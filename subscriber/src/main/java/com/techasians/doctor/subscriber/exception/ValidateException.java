package com.techasians.doctor.subscriber.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidateException  extends RuntimeException  {
    private Map<String,String> map;
    public ValidateException(String message) {
        super(message);
        map=new HashMap<>();
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public ValidateException() {
        map=new HashMap<>();
    }
    public ValidateException(String code,String message) {
        map=new HashMap<>();
        map.put(code,message);
    }
    public void push(String key,String value){
        map.put(key,value);
    }
}
