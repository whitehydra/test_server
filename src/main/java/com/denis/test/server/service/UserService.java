package com.denis.test.server.service;

import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;

import java.util.List;

//Слой сервисов для использования репозитория

public interface UserService {

    boolean checkAuthorization(AuthorizationForm authorizationForm);
    List<UserEntity> getAll();
    UserEntity getById(long id);
    UserEntity save(UserEntity firstEntity);
    void remove(long id);
}
