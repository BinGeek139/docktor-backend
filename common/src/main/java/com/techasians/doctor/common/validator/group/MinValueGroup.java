package com.techasians.doctor.common.validator.group;

public class MinValueGroup implements BaseGroup {

    double minValue = Double.MIN_VALUE;


    public MinValueGroup() {

    }

    public MinValueGroup(double min) {
        this.minValue = min;
    }

    public MinValueGroup ofMinValue(double min) {
        setMinValue(min);
        return this;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }


}
