package com.resolutionITS.application.entities;


public class Cost {

    int costid;
    String unit;
    double value;

    public Cost() {}

    public Cost(String unit, double value) {
        this.costid = costid;
        this.unit = unit;
        this.value = value;
    }

    public Cost(int costid, String unit, double value) {
        this.costid = costid;
        this.unit = unit;
        this.value = value;
    }

    public int getCostid() {
        return costid;
    }

    public void setCostid(int costid) {
        this.costid = costid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + (unit == null || unit.isEmpty() ? "" : " per " + unit);
    }
}
