package com.denis.test.server.controller;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService service;


    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity authorization(@RequestBody AuthorizationForm authorizationForm){
        return service.checkAuthorization(authorizationForm);
//        if (service.checkAuthorization(authorizationForm)){
//            return true;
//        }
//        else  return false;
    }

    //****************
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public List<UserEntity> getMainTest(){
        List<UserEntity> list = new ArrayList<>();
        list.add(createEmptyUser());
        return list;
    }
    //****************

    //Получение списка объектов
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<UserEntity> getAllUsers(){
       return service.getAll();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity getUser(@PathVariable("id") int userID){
        return service.getById(userID);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity addUser(@RequestBody UserEntity userEntity){
        return service.save(userEntity);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(@PathVariable int id){
        service.remove(id);
    }

    private UserEntity createEmptyUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId_usr(1);
        userEntity.setUsername("EMPTY");
        userEntity.setPassword("EMPTY");
        userEntity.setLevel("Student");
        return userEntity;
    }
}
