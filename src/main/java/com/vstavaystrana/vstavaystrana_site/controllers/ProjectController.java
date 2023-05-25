package com.vstavaystrana.vstavaystrana_site.controllers;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.News;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.services.BusinessmanService;
import com.vstavaystrana.vstavaystrana_site.services.NewsService;
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
    public ProjectController(ProjectService projectService, BusinessmanService businessmanService, NewsService newsService) {
        this.projectService = projectService;
        this.businessmanService = businessmanService;
        this.newsService = newsService;
    }

    private final ProjectService projectService;
    private final BusinessmanService businessmanService;
    private final NewsService newsService;

    @GetMapping("/{project_id}/about")
    public String getProjectAbout(Model model, @PathVariable Long project_id){
        Project project = projectService.findById(project_id);
        model.addAttribute("project", project);
        List<News> news = newsService.findByProject(project);
        model.addAttribute("news_list", news);
        model.addAttribute("new_news", new News());
        return "project_about";
    }

    @PostMapping("/news/add")
    public String addNewsCreate(Model model,
                                @RequestParam Long projectId,
                                @ModelAttribute("new_news")News news,
                                @AuthenticationPrincipal User user){
        Project project = projectService.findById(projectId);
        news.setAuthor(user.getUsername());
        news.setProject(project);
        newsService.saveNews(news);
        return  String.format("redirect:/projects/%s/about", project.getId());
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
    public String saveProject(@ModelAttribute("new_project") Project project,
                              @AuthenticationPrincipal User user) {
        Businessman author = businessmanService.findBusinessmanByUser(user);
        project.setAuthor(author);
        projectService.saveProject(project);
        return String.format("redirect:/projects/%s/about", project.getId());
    }

    @GetMapping("/my")
    public String getUsersProjects(@AuthenticationPrincipal User user, Model model){
        Businessman author = businessmanService.findBusinessmanByUser(user);
        List<Project> projs = projectService.findByAuthor(author);

        model.addAttribute("user", user);
        model.addAttribute("projects", projs);
        model.addAttribute("author", author);

        return "project_show";
    }

    @GetMapping("")
    public String getAllProjects(@AuthenticationPrincipal User user, Model model){
        List<Project> projs = projectService.getAllProjects();

        model.addAttribute("user", user);
        model.addAttribute("projects", projs);
        return "project_show";
    }



}
