package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    @Query(value = "SELECT id FROM investor WHERE user_id = :userId", nativeQuery = true)
    Long findIdByUserId(@Param("userId") Long userId);
}
