package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vstavaystrana.vstavaystrana_site.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    public ProjectService(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
    }

    private ProjectRepository projectRepo;

    public void saveProject(Project project) {
        projectRepo.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }
}
