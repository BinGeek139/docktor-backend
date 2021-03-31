package com.techasians.doctor.subscriber.utils;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Random;

public class Common {


    public static String generateNumber(int length) {
        Random random=new Random();
        String s = "";

        for (int i = 0; i < length; i++) {
            s += random.nextInt(10);
        }
        return s;
    }

    public static int lengthOfString(String str){
        if(Objects.isNull(str)){
            return 0;
        }
        return str.length();
    }
}
