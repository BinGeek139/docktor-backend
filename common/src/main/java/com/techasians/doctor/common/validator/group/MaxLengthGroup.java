package com.techasians.doctor.common.validator.group;

public class MaxLengthGroup implements BaseGroup {

    long max = 255;


    public MaxLengthGroup() {

    }

    public MaxLengthGroup(long max) {
        this.max = max;
    }


    public MaxLengthGroup ofMax(long max) {
        setMax(max);
        return this;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }
}
