package com.denis.test.server.repository;

import com.denis.test.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUsersByUsernameAndPassword(String username, String password);
}
