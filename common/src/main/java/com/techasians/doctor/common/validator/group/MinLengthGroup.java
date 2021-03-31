package com.techasians.doctor.common.validator.group;

public class MinLengthGroup implements BaseGroup {

    long min = 0;


    public MinLengthGroup() {

    }

    public MinLengthGroup(long min) {
        this.min = min;
    }



    public MinLengthGroup ofMin(long min) {
        setMin(min);
        return this;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }


}
