package com.resolutionITS.application.entities;

public class ResourceReport {
    private int esfid;
    private String esfdesc;
    private int totalResources;
    private int resourcesInUse;

    public ResourceReport(){}
    public ResourceReport(int esfid, String esfdesc, int totalResources, int resourcesInUse){
        this.esfid = esfid;
        this.esfdesc =esfdesc;
        this.totalResources = totalResources;
        this.resourcesInUse = resourcesInUse;
    }

    public ResourceReport(int esfid, String esfdesc, int resources){
        this.esfid = esfid;
        this.esfdesc =esfdesc;
        this.totalResources = resources;

    }




    public int getEsfid() {
        return esfid;
    }

    public int getTotalResources() {
        return totalResources;
    }

    public int getResourcesInUse() {
        return resourcesInUse;
    }

    public String getEsfdesc() {
        return esfdesc;
    }
}
