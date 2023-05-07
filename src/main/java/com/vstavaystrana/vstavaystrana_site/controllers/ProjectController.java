package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.services.BusinessmanService;
import com.vstavaystrana.vstavaystrana_site.services.ProjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/projects")
public class ProjectController {
    @Autowired
    public ProjectController(ProjectService projectService, BusinessmanService businessmanService) {
        this.projectService = projectService;
        this.businessmanService = businessmanService;
    }

    private final ProjectService projectService;
    private final BusinessmanService businessmanService;

    @GetMapping("/about/{project_id}")
    public String getProjectAbout(Model model, @PathVariable Long project_id){
        Project project = projectService.findById(project_id);
        model.addAttribute("project", project);
        return "project_about";
    }

    @GetMapping("/create")
    public String getProjectCreation(@AuthenticationPrincipal User user, Model model) {
        Businessman author = businessmanService.findBusinessmanByUser(user);
        //if(author == null) return "projects";

        model.addAttribute("user", user);
        model.addAttribute("new_project", new Project());
        model.addAttribute("author", author);
        return "project_registration";
    }

    @PostMapping("/create")
    public String saveProject(@ModelAttribute("new_project") Project project, @AuthenticationPrincipal User user) {


        projectService.saveProject(project);
        return String.format("redirect:/projects/about/%s", project.getId());
    }

    @GetMapping("/{businessman_id}")
    public String getAllbusinessmansProjects(@AuthenticationPrincipal User user, Model model,@PathVariable Long businessman_id){
        Businessman author = businessmanService.findById(businessman_id);
        List<Project> projs = projectService.findByAuthor(author);

        model.addAttribute("user", user);
        model.addAttribute("projects", projs);
        model.addAttribute("author", author);
        return "projects";
    }
}
