package com.vstavaystrana.vstavaystrana_site.controllers;


import com.vstavaystrana.vstavaystrana_site.models.Investment;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.services.InvestmentService;
import com.vstavaystrana.vstavaystrana_site.services.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/investment")
public class InvestmentController {
    @Autowired
    public InvestmentController(InvestmentService investmentService, ProjectService projectService) {
        this.investmentService = investmentService;
        this.projectService = projectService;
    }

    private final InvestmentService investmentService;
    private final ProjectService projectService;

    @GetMapping("/create/{project_id}")
    public String getInvestmentCreation(@AuthenticationPrincipal User user, Model model, @PathVariable Long project_id) {
        model.addAttribute("user", user);
        Project project = projectService.findById(project_id);
        model.addAttribute("project", project);
        Investment investment = new Investment();
        model.addAttribute("new_investment", investment);
        return "investment_create";
    }

    @PostMapping("/create/{project_id}")
    public String postInvestmentCreation(@AuthenticationPrincipal User user, @ModelAttribute("new_investment") Investment investment, Model model, @PathVariable Long project_id) throws IOException {
        investment.setDate(LocalDate.now());
        Project project = projectService.findById(project_id);
        investment.setProject(project);
        investment.setInvestor(user);
        investmentService.saveInvestment(investment);
        return "redirect:/";
    }

    @GetMapping("/my")
    public String GetMyInvestments(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("investments", investmentService.findByInvestor(user));
        return "my_investments";
    }
}
