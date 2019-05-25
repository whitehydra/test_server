package com.denis.test.server.repository;

import com.denis.test.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUsersByUsernameAndPassword(String username, String password);
    UserEntity findByUsername(String username);
    UserEntity findByNameAndFacultyAndStudyGroup(String name, String faculty, String studyGroup);
}
