package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepository extends CrudRepository<News, Long> {
}
