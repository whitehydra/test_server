package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioCriterionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriterionRepository extends JpaRepository<PortfolioCriterionEntity, Integer> {
}
