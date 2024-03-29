package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.services.BusinessmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/businessman")
public class BusinessmanController {
    @Autowired
    public BusinessmanController(BusinessmanService service){
        this.businessmanService = service;
    }

    private final BusinessmanService businessmanService;

    @GetMapping("/about/{businessman_id}")
    public String getBusinessmanAbout(@AuthenticationPrincipal User user, Model model, @PathVariable Long businessman_id){
        Businessman businessman = businessmanService.findById(businessman_id);
        model.addAttribute("businessman", businessman);
        model.addAttribute("user", user);
        return "businessman_about";
    }
    @GetMapping("/create")
    public String getBusinessmanCreation(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        var businessman = businessmanService.findBusinessmanByUser(user);
        model.addAttribute("businessman", businessman);
        model.addAttribute("new_businessman", new Businessman());
        return "businessman_registration";
    }

    @PostMapping("/create")
    public String saveBusinessman(@ModelAttribute("new_businessman") Businessman businessman, @AuthenticationPrincipal User user){
        businessman.setUser(user);
        businessmanService.saveBusinessman(businessman);
        return "redirect:/";
    }
}
