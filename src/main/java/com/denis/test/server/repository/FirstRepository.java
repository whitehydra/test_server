package com.denis.test.server.repository;

import com.denis.test.server.entity.FirstEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstRepository extends JpaRepository<FirstEntity, Long> {


}
