package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
