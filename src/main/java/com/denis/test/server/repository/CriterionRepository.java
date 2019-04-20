package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioCategoryEntity;
import com.denis.test.server.entity.PortfolioCriterionEntity;
import com.denis.test.server.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriterionRepository extends JpaRepository<PortfolioCriterionEntity, Integer> {
}
