package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.News;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import com.vstavaystrana.vstavaystrana_site.repositories.NewsRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public NewsService(NewsRepository repo, ProjectRepository projectRepository) {
        this.newsRepository = repo;
        this.projectRepository = projectRepository;
    }


    public void saveNews(News news){
        news.setPublishedOn(LocalDateTime.now());
        newsRepository.save(news);
    }

    public List<News> findByProject(Project proj){
        return newsRepository.findByProjectOrderByPublishedOnDesc(proj);
    }




}
