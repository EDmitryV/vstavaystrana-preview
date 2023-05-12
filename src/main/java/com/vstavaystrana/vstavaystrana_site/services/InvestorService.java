package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.repositories.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvestorService {
    private final InvestorRepository investorRepository;

    @Autowired
    public InvestorService(InvestorRepository repo) {
        this.investorRepository = repo;
    }

    public Long findInvestorIdByUserId(Long userId) {
        return investorRepository.findIdByUserId(userId);
    }
public Investor findInvestorById(Long id){
        return investorRepository.findById(id).orElse(null);
}
    public void saveInvestor(Investor investor) {
        investorRepository.save(investor);
    }
}
