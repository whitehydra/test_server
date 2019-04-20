package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<PortfolioCategoryEntity, Integer> {

}
