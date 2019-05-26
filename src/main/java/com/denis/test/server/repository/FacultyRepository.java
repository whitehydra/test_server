package com.denis.test.server.repository;

import com.denis.test.server.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<FacultyEntity, Integer> {
}
