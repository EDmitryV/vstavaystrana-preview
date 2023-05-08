package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.vstavaystrana.vstavaystrana_site.repositories.ProjectRepository;
import org.springframework.web.server.ResponseStatusException;

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


    public Project findById(Long id){
        return projectRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with id: " + id));
    }

    public List<Project> findByAuthor(Businessman author){
        List<Project> result = projectRepo.findByAuthor(author);
        if(result.size()==0) throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found with author: " + author.getId());
        return result;
    }

}
