package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InvestorService {
    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository repo) {
        this.investorRepository = repo;
    }

    public Investor findInvestorByUser(User user){
        return investorRepository.findByUser(user).get(0);
    }

    public void saveInvestor(Investor investor){
        investorRepository.save(investor);
    }
}
