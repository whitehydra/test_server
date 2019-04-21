package com.denis.test.server.repository;

import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.entity.PortfolioFileEntity;
import com.denis.test.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<PortfolioFileEntity, Integer> {
   // List<PortfolioFileEntity> findByAuthor_Username(Integer id);
}
