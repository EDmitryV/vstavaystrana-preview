package com.vstavaystrana.vstavaystrana_site.controllers;


import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String registration(Model model)
    {
        model.addAttribute("user", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String adduser(@ModelAttribute("user") User userForm, BindingResult bindingResult, Model model)
    {
//        try
//        {
//            userService.addUser(user);
//            return "redirect:/login";
//        }
//        catch (Exception ex)
//        {
//            model.addAttribute("message", "User exists");
//            return "registration";
//        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/";
    }

}
