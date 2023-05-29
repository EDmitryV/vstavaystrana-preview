package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AgreementController {
    @GetMapping("/agreement")
    public String getInvestorCreation(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        return "agreement";
    }
}
