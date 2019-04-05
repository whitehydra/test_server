package com.denis.test.server.service;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import java.util.List;

//Слой сервисов для использования репозитория

public interface UserService {
    UserEntity checkAuthorization(AuthorizationForm authorizationForm);
    List<UserEntity> getAll();
    UserEntity getById(int id);
    UserEntity save(UserEntity firstEntity);
    void remove(int id);
}
