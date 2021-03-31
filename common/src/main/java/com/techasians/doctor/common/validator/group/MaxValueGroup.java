package com.techasians.doctor.common.validator.group;

public class MaxValueGroup implements BaseGroup {

    double maxValue = Double.MAX_VALUE;


    public MaxValueGroup() {

    }

    public MaxValueGroup(long max) {
        this.maxValue = max;
    }


    public MaxValueGroup ofMaxValue(double max) {
        setMaxValue(max);
        return this;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
