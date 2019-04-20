package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<PortfolioTypeEntity, Integer> {

}
