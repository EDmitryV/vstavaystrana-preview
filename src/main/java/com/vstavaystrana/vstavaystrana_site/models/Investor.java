package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.*;

@Entity
public class Investor extends AbstractEntity{
    @OneToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Boolean activity_allowed;

    @Lob
    private Byte[] agreement;

    public Byte[] getAgreement() {
        return agreement;
    }

    public void setAgreement(Byte[] agreement) {
        this.agreement = agreement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getActivity_allowed() {
        return activity_allowed;
    }

    public void setActivity_allowed(Boolean activity_allowed) {
        this.activity_allowed = activity_allowed;
    }
}
