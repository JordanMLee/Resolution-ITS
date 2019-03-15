package com.resolutionITS.application.entities;

public class TestClass extends technician {
    //this is a custom class for search technician
    String unit;
    double value;
    technician technician;

    public TestClass() {
    }

    public TestClass(technician technician, String unit, double value) {
        this.technician = technician;
        this.unit = unit;
        this.value = value;
    }

    public technician getTechnician() {
        return technician;
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


}
