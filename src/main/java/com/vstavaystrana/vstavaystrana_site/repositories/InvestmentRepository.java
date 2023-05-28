package com.vstavaystrana.vstavaystrana_site.repositories;

import com.vstavaystrana.vstavaystrana_site.models.Investment;
import com.vstavaystrana.vstavaystrana_site.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment,Long> {
    List<Investment> findByInvestor(User user);
}
