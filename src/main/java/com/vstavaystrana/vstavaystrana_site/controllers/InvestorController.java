package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.services.InvestorService;
import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = "/investor")
public class InvestorController {
    @Autowired
    public InvestorController(InvestorService service) {
        this.investorService = service;
    }

    private final InvestorService investorService;

    @GetMapping("/create")
    public String getInvestorCreation(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        var investorId = investorService.findInvestorIdByUserId(user.getId());
        Investor investor;
        if(investorId!=null){
            investor = investorService.findInvestorById(investorId);
        }
        else {
            investor = null;
        }
        model.addAttribute("investor", investor);
        model.addAttribute("new_investor", new Investor());
        return "investor_registration";
    }

    @PostMapping("/create")
    public String postInvestorCreation(@AuthenticationPrincipal User user,
                             @ModelAttribute("new_investor") Investor investor,
                             Model model,
                             @RequestParam("imagefile") MultipartFile file) throws IOException {

        Byte[] byteObjects = convertToBytes(file);
        investor.setAgreement(byteObjects);
        investor.setUser(user);
        investor.setActivity_allowed(false);
        investorService.saveInvestor(investor);
        return "redirect:/";
    }

    private Byte[] convertToBytes(MultipartFile file) throws IOException {
        Byte[] byteObjects = new Byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObjects[i++] = b;
        }
        return byteObjects;
    }
}
