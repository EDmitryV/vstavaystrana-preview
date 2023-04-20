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

    private final ProjectService projectService;

    @GetMapping("/projects")
    public String getProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
//        model.addAttribute("new_project", new Project());
        return "projects";
    }

    @PostMapping("/project/add")
    public String savePerson(@ModelAttribute("new_project") Project project) {
        projectService.saveProject(project);
        return "redirect:/";
    }
}
