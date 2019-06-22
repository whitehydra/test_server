package com.denis.test.server.service;
import com.denis.test.server.entity.FacultyEntity;
import com.denis.test.server.entity.GroupEntity;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import java.util.List;

//Слой сервисов для использования репозитория

public interface UserService {
    UserEntity checkAuthorization(AuthorizationForm authorizationForm);
    List<UserEntity> getAll();
    UserEntity getById(int id);
    FacultyEntity getFacultyById(int id);
    GroupEntity getGroupById(int id);
    UserEntity save(UserEntity firstEntity);
    FacultyEntity saveFaculty(FacultyEntity facultyEntity);
    void saveFaculties(List<FacultyEntity> facultyEntities);
    GroupEntity saveGroup(GroupEntity groupEntity);
    void saveGroups(List<GroupEntity> groupEntities);
    void update(UserEntity userEntity);
    UserEntity findByUsername(String username);
    UserEntity findByAnotherData(String name, String faculty, String studyGroup);
    FacultyEntity addGroups(FacultyEntity faculty, List<GroupEntity> groups);
    List<FacultyEntity> getFacultiesList();
    List<GroupEntity> getGroupsList();
    List<GroupEntity> getGroupListByFacultyId(int id);

    String getToken(UserEntity userEntity);
    void setToken(String username, String token);
    void setAvatar(String username, String avatar);
    void remove(int id);

    String accessRecovery(String username);
    String pinRecovery(String username);
}
