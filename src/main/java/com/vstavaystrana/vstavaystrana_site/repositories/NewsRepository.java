package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.News;
import com.vstavaystrana.vstavaystrana_site.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.PrinterJob;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByProjectOrderByPublishedOnDesc(Project project);
}
