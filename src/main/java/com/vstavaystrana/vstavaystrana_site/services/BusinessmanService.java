package com.vstavaystrana.vstavaystrana_site.services;


import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Investor;
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

    @Autowired
    public BusinessmanService(BusinessmanRepository repo) {
        this.businessmanRepository = repo;
    }

    public Businessman findBusinessmanByUser(User user){
        return businessmanRepository.findByUser(user).get(0);
    }

    public void saveBusinessman(Businessman businessman){
        businessmanRepository.save(businessman);
    }

    public Businessman findById(Long id){
        return businessmanRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Businessman not found with id: " + id));
    }
}
