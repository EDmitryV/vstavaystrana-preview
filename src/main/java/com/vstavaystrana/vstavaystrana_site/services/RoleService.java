package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }
}
