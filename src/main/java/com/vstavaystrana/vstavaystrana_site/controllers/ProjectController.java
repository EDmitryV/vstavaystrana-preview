package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.services.ProjectService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProjectController {
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    ProjectService projectService;

    @GetMapping("/")
    public String getMain(Model model) {
        List<Project> project = projectService.getAllProjects();
        model.addAttribute("project", project);
        model.addAttribute("new_project", new Project());
        return "index";
    }

    @PostMapping("/project/add")
    public String savePerson(@ModelAttribute("new_project") Project project) {
        projectService.saveProject(project);
        return "redirect:/";
    }
}
