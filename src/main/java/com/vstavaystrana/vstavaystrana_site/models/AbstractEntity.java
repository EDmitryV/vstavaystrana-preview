package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractEntity implements DatabaseEntity
{
        @Id
        @GeneratedValue
        protected Long id;

        @Override
        public Long getId()
        {
            return id;
        }
}
