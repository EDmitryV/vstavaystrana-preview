package com.vstavaystrana.vstavaystrana_site.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name="t_role")
public class Role implements GrantedAuthority {


    public enum Names {
        ROLE_USER(1),
        ROLE_ADMIN(2),
        ROLE_INVESTOR(3),
        ROLE_BUSINESSMAN(4);

        private int nameId;

        Names(int id) {
            nameId = id;
        }

        public int getId(){return nameId;}
    }

    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}