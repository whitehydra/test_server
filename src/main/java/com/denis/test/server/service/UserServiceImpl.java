package com.denis.test.server.service;
import com.denis.test.server.entity.FacultyEntity;
import com.denis.test.server.entity.GroupEntity;
import com.denis.test.server.entity.PortfolioCategoryEntity;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.other.Functions;
import com.denis.test.server.repository.FacultyRepository;
import com.denis.test.server.repository.GroupRepository;
import com.denis.test.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MailSender mailSender;




    @Override
    public UserEntity checkAuthorization(AuthorizationForm authorizationForm) {
        UserEntity userEntity = userRepository.findUsersByUsernameAndPassword(authorizationForm.getUsername(),
                authorizationForm.getPassword());
        return userEntity;
    }



    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getById(int id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()){
            return userEntity.get();
        }
        else return null;
    }

    @Override
    public FacultyEntity getFacultyById(int id){
        Optional<FacultyEntity> facultyEntity = facultyRepository.findById(id);
        if(facultyEntity.isPresent()){
            return facultyEntity.get();
        }
        else return null;
    }

    @Override
    public GroupEntity getGroupById(int id){
        Optional<GroupEntity> groupEntity = groupRepository.findById(id);
        if(groupEntity.isPresent()){
            return groupEntity.get();
        }
        else return null;
    }



    @Override
    public UserEntity save(UserEntity userEntity) {

        try {
            userEntity.setPassword(Functions.generateHash(userEntity.getPassword()));
            userEntity.setAvatar("default.jpg");
            return userRepository.saveAndFlush(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public FacultyEntity saveFaculty(FacultyEntity facultyEntity){
        try {
            return facultyRepository.saveAndFlush(facultyEntity);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void saveFaculties(List<FacultyEntity> facultyEntities){
        for (FacultyEntity faculty:facultyEntities){
            saveFaculty(faculty);
        }
    }

    @Override
    public GroupEntity saveGroup(GroupEntity groupEntity){
        try {
            return groupRepository.saveAndFlush(groupEntity);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void saveGroups(List<GroupEntity> groupEntities){
        for (GroupEntity group:groupEntities){
            saveGroup(group);
        }
    }



    @Override
    public void update(UserEntity userEntity){
        userRepository.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByAnotherData(String name, String faculty, String studyGroup){
        return userRepository.findByNameAndFaculty_ShortNameAndGroup_ShortName(name,faculty,studyGroup);
    }


    @Override
    public FacultyEntity addGroups(FacultyEntity faculty, List<GroupEntity> groups){
        faculty.setGroups(groups);
        return facultyRepository.saveAndFlush(faculty);
    }

    @Override
    public List<FacultyEntity> getFacultiesList(){
        return facultyRepository.findAll();
    }

    @Override
    public List<GroupEntity> getGroupsList(){
        return groupRepository.findAll();
    }


    @Override
    public List<GroupEntity> getGroupListByFacultyId(int id){
        FacultyEntity faculty = getFacultyById(id);
        if(faculty!=null) return getFacultyById(id).getGroups();
        return null;
    }

    @Override
    public String getToken(UserEntity userEntity) {
        return userRepository.findUsersByUsernameAndPassword(userEntity.getUsername(),userEntity.getPassword()).getToken();
    }

    @Override
    public void setToken(String username, String token){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setToken(token);
        userRepository.save(userEntity);
    }

    @Override
    public void setAvatar(String username, String avatar){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setAvatar(avatar);
        userRepository.save(userEntity);
    }

    @Override
    public void remove(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public String accessRecovery(String username){
        String subject = "Восстановление доступа";
        String message = "Пароль доступа был сброшен. Новый пароль: ";
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity!=null){
            String mail = userEntity.getMail();
            if((mail != null) && (!mail.equals(""))){
                String newPass = Functions.randomPassword(8);
                String hashPass = "";
                try {
                    hashPass = Functions.generateHash(newPass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userEntity.setPassword(hashPass);
                userRepository.saveAndFlush(userEntity);


                mailSender.send(userEntity.getMail(), subject, message + newPass);
                return "Письмо отправлено";

            }
            else return  "Почта не указана";
        }
        else return "Пользователь не найден";
    }

    @Override
    public String pinRecovery(String username){
        String subject = "Восстановление PIN-кода";
        String message = "Ваш PIN-код: ";
        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity!=null){
            String pin = userEntity.getPin();
            if((pin != null) && (!pin.equals(""))){
                mailSender.send(userEntity.getMail(), subject, message + userEntity.getPin());
                return "Письмо отправлено";
            }
            else return  "Почта не указана";
        }
        else return "Пользователь не найден";
    }
}
