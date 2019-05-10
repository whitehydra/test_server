package com.denis.test.server.service;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.other.Functions;
import com.denis.test.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    //@Autowired
    //private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository repository;



    @Override
    public UserEntity checkAuthorization(AuthorizationForm authorizationForm) {
        UserEntity userEntity = repository.findUsersByUsernameAndPassword(authorizationForm.getUsername(),
                authorizationForm.getPassword());
        return userEntity;
    }



    @Override
    public List<UserEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public UserEntity getById(int id) {
        Optional<UserEntity> userEntity = repository.findById(id);
        if (userEntity.isPresent()){
            return userEntity.get();
        }
        else return null;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {

        try {
            userEntity.setPassword(Functions.generateHash(userEntity.getPassword()));
            userEntity.setAvatar("default.jpg");
            return repository.saveAndFlush(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(UserEntity userEntity){
        repository.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username);
    }



    @Override
    public String getToken(UserEntity userEntity) {
        return repository.findUsersByUsernameAndPassword(userEntity.getUsername(),userEntity.getPassword()).getToken();
    }

    @Override
    public void setToken(String username, String token){
        UserEntity userEntity = repository.findByUsername(username);
        userEntity.setToken(token);
        repository.save(userEntity);
    }

    @Override
    public void setAvatar(String username, String avatar){
        UserEntity userEntity = repository.findByUsername(username);
        userEntity.setAvatar(avatar);
        repository.save(userEntity);
    }


    @Override
    public void remove(int id) {
        repository.deleteById(id);
    }
}
