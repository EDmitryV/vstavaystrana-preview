package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InvestorService {
    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository repo) {
        this.investorRepository = repo;
    }

    public Investor findInvestorByUser(User user) {
        try{
            return investorRepository.findByUser(user).get(0);
        }catch (RuntimeException e){
            return null;
        }
    }

    public void saveInvestor(Investor investor) {
        investorRepository.save(investor);
    }
}
