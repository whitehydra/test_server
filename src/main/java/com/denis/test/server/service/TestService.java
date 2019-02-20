package com.denis.test.server.service;

import com.denis.test.server.entity.FirstEntity;

import java.util.List;

public interface TestService {
    List<FirstEntity> getAll();
    FirstEntity getById(long id);
    FirstEntity save(FirstEntity firstEntity);
    void remove(long id);
}
