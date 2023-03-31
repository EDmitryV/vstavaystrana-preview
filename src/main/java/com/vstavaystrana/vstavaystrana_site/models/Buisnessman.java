package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.Entity;

@Entity
public class Buisnessman extends AbstractEntity
{
    protected Integer userId;
    protected Byte[] passportScan;
    protected Float readiness;
    protected Boolean haveCompletedProject;
    protected Integer bankRequisites;
    protected String info;
    protected Boolean activityAllowed;
}
