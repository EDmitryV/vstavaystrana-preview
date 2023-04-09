package com.vstavaystrana.vstavaystrana_site.services;


import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessmanService {
    private final BusinessmanRepository businessmanRepository;

    @Autowired
    public BusinessmanService(BusinessmanRepository repo){
        this.businessmanRepository = repo;
    }
}
