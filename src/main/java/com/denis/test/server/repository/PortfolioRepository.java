package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Integer> {
    //************
}
