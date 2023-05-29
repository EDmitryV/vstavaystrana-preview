package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.InvestorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestorService {
    private final InvestorRepository investorRepository;
    private final UserService userService;

    @Autowired
    public InvestorService(InvestorRepository repo, UserService userService) {
        this.investorRepository = repo;
        this.userService = userService;
    }

    public Investor findInvestorByUser(User user) {
        try {
            return investorRepository.findByUser(user).get(0);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void saveInvestor(Investor investor) {
        investorRepository.save(investor);
//        userService.assignRole(investor.getUser(), Role.Names.ROLE_INVESTOR);
    }
}
