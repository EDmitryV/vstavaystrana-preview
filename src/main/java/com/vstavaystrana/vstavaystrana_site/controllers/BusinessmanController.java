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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        var businessmanId = businessmanService.findBusinessmanIdByUserId(user.getId());
        Businessman businessman;
        if(businessmanId != null){
            businessman = businessmanService.findById(businessmanId);
        }else {
            businessman = null;
        }
        model.addAttribute("businessman", businessman);
        model.addAttribute("new_businessman", new Businessman());
        return "businessman_registration";
    }

    @PostMapping("/create")
    public String postBusinessmanCreation(@AuthenticationPrincipal User user,
                             @ModelAttribute("new_businessman") Businessman businessman,
                             Model model,
                             @RequestParam("imagefile") MultipartFile file) throws IOException {
        Byte[] byteObjects = convertToBytes(file);
        businessman.setAgreement(byteObjects);
        businessman.setUser(user);
        businessman.setActivity_allowed(false);
        businessmanService.saveBusinessman(businessman);
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
