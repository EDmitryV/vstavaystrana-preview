package com.vstavaystrana.vstavaystrana_site.services;

import com.vstavaystrana.vstavaystrana_site.models.News;
import com.vstavaystrana.vstavaystrana_site.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository repo){
        this.newsRepository = repo;
    }


}
