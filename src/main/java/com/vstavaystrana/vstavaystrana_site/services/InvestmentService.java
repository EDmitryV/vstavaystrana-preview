package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.repositories.InvestmentRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestmentService {
    private final InvestmentRepository investRepository;

    @Autowired
    public InvestmentService(InvestmentRepository repo){
        this.investRepository = repo;
    }
}
