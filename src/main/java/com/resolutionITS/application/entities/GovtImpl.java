package com.resolutionITS.application.entities;

public class GovtImpl extends Users {

    private String agencyoffice;

    public GovtImpl() {}

    public GovtImpl(String agencyoffice) {
        this.agencyoffice = agencyoffice;
    }

    public String getAgencyoffice() {
        return agencyoffice;
    }

    public void setAgencyoffice(String agencyoffice) {
        this.agencyoffice = agencyoffice;
    }
}
