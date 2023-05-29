package com.vstavaystrana.vstavaystrana_site.config;

import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitDataLoader implements ApplicationRunner {

    private RoleRepository roleRepository;


    @Autowired
    public InitDataLoader(RoleRepository roleRepo) {
        this.roleRepository = roleRepo;
    }

    public void run(ApplicationArguments args) {
        for(Role.Names roleName: Role.Names.values())
        {
            if(!roleRepository.existsByName(roleName.name())){
                roleRepository.save(
                        new Role(
                            (long) roleName.getId(),
                            roleName.name()));
            }
        }
    }
}
