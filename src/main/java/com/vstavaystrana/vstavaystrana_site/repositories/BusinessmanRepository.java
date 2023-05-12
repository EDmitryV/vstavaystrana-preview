package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Investor;
import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessmanRepository extends JpaRepository<Businessman, Long> {

    @Query(value = "SELECT id FROM businessman WHERE user_id = :userId", nativeQuery = true)
    Long findIdByUserId(@Param("userId")Long id);
}
