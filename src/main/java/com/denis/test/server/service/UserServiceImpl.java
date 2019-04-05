package com.denis.test.server.service;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserEntity checkAuthorization(AuthorizationForm authorizationForm) {
        UserEntity userEntity = repository.findUsersByUsernameAndPassword(authorizationForm.getUsername(),
                authorizationForm.getPassword());
        return userEntity;

//        if(userEntity!=null)return true;
//        else return false;
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
        return repository.saveAndFlush(userEntity);
    }

    @Override
    public void remove(int id) {
        repository.deleteById(id);
    }
}
