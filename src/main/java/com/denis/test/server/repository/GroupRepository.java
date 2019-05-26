package com.denis.test.server.repository;

import com.denis.test.server.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {
}
