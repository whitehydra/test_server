package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioFileEntity;
import com.denis.test.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<PortfolioFileEntity, Integer> {
}
