package com.vstavaystrana.vstavaystrana_site.services;


import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BusinessmanService {
    private final BusinessmanRepository businessmanRepository;

    private final UserService userService;

    @Autowired
    public BusinessmanService(BusinessmanRepository repo,UserService userService) {
        this.businessmanRepository = repo;
        this.userService = userService;
    }

    public Long findBusinessmanIdByUserId(Long id){
            return businessmanRepository.findIdByUserId(id);
    }

    public void saveBusinessman(Businessman businessman){
        businessmanRepository.save(businessman);
        userService.assignRole(businessman.getUser(), Role.Names.ROLE_BUSINESSMAN);
    }

    public Businessman findById(Long id){
        return businessmanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Businessman not found with id: " + id));
    }
}
