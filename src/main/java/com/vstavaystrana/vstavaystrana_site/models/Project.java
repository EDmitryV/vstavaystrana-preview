package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projects")
public class Project extends AbstractEntity{
    public Project(byte doc_scan,
                   String name,
                   String description,
                   String risks,
                   String promo,
                   String addr,
                   int stage,
                   Date date_of_creation,
                   Date date_of_completion,
                   int funds_required,
                   int funds_raised,
                   int funds_by_author,
                   String rewards_description,
                   int support) {
        this.doc_scan = doc_scan;
        this.name = name;
        this.description = description;
        this.risks = risks;
        this.promo = promo;
        this.addr = addr;
        this.stage = stage;
        this.date_of_creation = date_of_creation;
        this.date_of_completion = date_of_completion;
        this.funds_required = funds_required;
        this.funds_raised = funds_raised;
        this.funds_by_author = funds_by_author;
        this.revards_description = rewards_description;
        this.support = support;
    }

    public Project(){}

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Businessman author;
    private byte doc_scan;
    private String name;
    private String description;
    private String risks;
    private String promo;
    private String addr;
    private int stage;

    @Column(name="date_of_creation")
    @Temporal(TemporalType.DATE)
    private Date date_of_creation;

    @Column(name="date_of_completion")
    @Temporal(TemporalType.DATE)
    private Date date_of_completion;

    private int funds_required;

    private int funds_raised;
//    todo add author connection

    private int funds_by_author;

    private String revards_description;

    private int support;

    // todo add news line connection
//    @Column(name="date_of_com")
//
//    public Long getId() {
//        return id;
//    }

    public byte getDoc_scan() {
        return doc_scan;
    }

    public void setDoc_scan(byte doc_scan) {
        this.doc_scan = doc_scan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRisks() {
        return risks;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    public void setDate_of_creation(Date date_of_creation) {
        this.date_of_creation = date_of_creation;
    }

    public Date getDate_of_completion() {
        return date_of_completion;
    }

    public void setDate_of_completion(Date date_of_completion) {
        this.date_of_completion = date_of_completion;
    }

    public int getFunds_required() {
        return funds_required;
    }

    public void setFunds_required(int funds_required) {
        this.funds_required = funds_required;
    }

    public int getFunds_raised() {
        return funds_raised;
    }

    public void setFunds_raised(int funds_raised) {
        this.funds_raised = funds_raised;
    }

    public int getFunds_by_author() {
        return funds_by_author;
    }

    public void setFunds_by_author(int funds_by_author) {
        this.funds_by_author = funds_by_author;
    }

    public String getRevards_description() {
        return revards_description;
    }

    public void setRevards_description(String revards_description) {
        this.revards_description = revards_description;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }
}
