package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    //************

   // @Query("select * from tr_portfolio join tr_user where")
    List<PortfolioEntity> findByAuthor_Username(String username);
}
