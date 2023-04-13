package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Buisnessman extends AbstractEntity
{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    //protected Long userId;



    protected Byte[] passportScan;
    protected Float readiness;
    protected Boolean haveCompletedProject;
    protected Integer bankRequisites;
    protected String info;
    protected Boolean activityAllowed;
}
