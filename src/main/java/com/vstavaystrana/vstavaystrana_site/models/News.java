package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class News extends AbstractEntity
{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    protected Project project;
    protected String author;
    protected String content;
    protected String photoUrl;
    protected String videoUrl;
}
