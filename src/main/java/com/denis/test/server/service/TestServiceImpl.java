package com.denis.test.server.service;

import com.denis.test.server.entity.FirstEntity;
import com.denis.test.server.repository.FirstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private FirstRepository repository;



    @Override
    public List<FirstEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public FirstEntity getById(long id) {
        Optional<FirstEntity> firstEntity = repository.findById(id);
        if (firstEntity.isPresent()){
            return firstEntity.get();
        }
        else return null;
    }

    @Override
    public FirstEntity save(FirstEntity firstEntity) {
        return repository.saveAndFlush(firstEntity);
    }

    @Override
    public void remove(long id) {
        repository.deleteById(id);
    }
}
