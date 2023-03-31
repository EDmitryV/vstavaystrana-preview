package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.Entity;

@Entity
public class News extends AbstractEntity
{
    protected Integer projectId;
    protected String author;
    protected String content;
    protected String photoUrl;
    protected String videoUrl;
}
