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
    void update(UserEntity userEntity);
    UserEntity findByUsername(String username);
    UserEntity findByAnoutherData(String name, String faculty, String studyGroup);
    String getToken(UserEntity userEntity);
    void setToken(String username, String token);
    void setAvatar(String username, String avatar);
    void remove(int id);
}
