package com.resolutionITS.application.entities;

public class Issue_declaration {

    String declaration;
    int uniqid;
    int issueid;

    public Issue_declaration() {
    }

    public Issue_declaration(String declaration, int uniqid, int issueid) {
        this.declaration = declaration;
        this.uniqid = uniqid;
        this.issueid = issueid;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public int getissueid() {
        return issueid;
    }

    public void setissueid(int issueid) {
        this.issueid = issueid;
    }

    public int getUniqid() {
        return uniqid;
    }

    public void setUniqid(int uniqid) {
        this.uniqid = uniqid;
    }

    @Override
    public String toString() {
        return declaration;
    }
}
