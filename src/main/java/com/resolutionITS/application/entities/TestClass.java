package com.resolutionITS.application.entities;

public class TestClass extends Resource {
    //this is a custom class for search resource
    String unit;
    double value;
    Resource resource;

    public TestClass() {}

    public TestClass(Resource resource, String unit, double value) {
        this.resource = resource;
        this.unit = unit;
        this.value = value;
    }

    public Resource getResource() {
        return resource;
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
