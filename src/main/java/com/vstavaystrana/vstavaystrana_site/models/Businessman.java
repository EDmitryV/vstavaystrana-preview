package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Businessman extends AbstractEntity
{
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Byte[] passport_scan;
    private Float readiness;
    private Boolean have_completed_project;
    private Integer bank_requisites;
    private String info;
    private Boolean activity_allowed;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Byte[] getPassport_scan() {
        return passport_scan;
    }

    public void setPassport_scan(Byte[] passport_scan) {
        this.passport_scan = passport_scan;
    }

    public Float getReadiness() {
        return readiness;
    }

    public void setReadiness(Float readiness) {
        this.readiness = readiness;
    }

    public Boolean getHave_completed_project() {
        return have_completed_project;
    }

    public void setHave_completed_project(Boolean have_completed_project) {
        this.have_completed_project = have_completed_project;
    }

    public Integer getBank_requisites() {
        return bank_requisites;
    }

    public void setBank_requisites(Integer bank_requisites) {
        this.bank_requisites = bank_requisites;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Boolean getActivity_allowed() {
        return activity_allowed;
    }

    public void setActivity_allowed(Boolean activity_allowed) {
        this.activity_allowed = activity_allowed;
    }
}
