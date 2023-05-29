package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Investment;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.InvestmentRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentService {
    private final InvestmentRepository investRepository;

    @Autowired
    public InvestmentService(InvestmentRepository repo) {
        this.investRepository = repo;
    }

    public void saveInvestment(Investment investment) {
        var project = investment.getProject();
        var user = investment.getInvestor();
        if(project.getAuthor().getUser().getId().equals(user.getId())){
            project.setFunds_by_author(project.getFunds_by_author() + investment.getSum());
        }
        project.setFunds_raised(project.getFunds_raised() + investment.getSum());
        investRepository.save(investment);
    }

    public List<Investment> findByInvestor(User user) {
        return investRepository.findByInvestor(user);
    }
}
