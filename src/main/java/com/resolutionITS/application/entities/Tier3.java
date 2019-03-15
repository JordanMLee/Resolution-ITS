package com.resolutionITS.application.entities;

public class Tier3 extends Users {

    private String agencyoffice;

    public Tier3() {
    }

    public Tier3(String agencyoffice) {
        this.agencyoffice = agencyoffice;
    }

    public String getAgencyoffice() {
        return agencyoffice;
    }

    public void setAgencyoffice(String agencyoffice) {
        this.agencyoffice = agencyoffice;
    }
}
