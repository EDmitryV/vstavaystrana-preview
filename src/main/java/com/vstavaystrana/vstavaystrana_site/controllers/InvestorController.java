package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.services.InvestorService;
import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InvestorController {
    @Autowired
    public InvestorController(InvestorService service){
        this.investorService = service;
    }

    private final InvestorService investorService;

    @GetMapping("/investor/create")
    public String getInvestorCreation(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        var investor = investorService.findInvestorByUser(user);
        model.addAttribute("investor", investor);
        model.addAttribute("new_investor", new Investor());
        return "investor_registration";
    }

    @PostMapping("/investor/create")
    public String saveInvestor(@ModelAttribute("new_investor") Investor investor, @AuthenticationPrincipal User user){
        investor.setUser(user);
        investorService.saveInvestor(investor);
        return "redirect:/";
    }
}
