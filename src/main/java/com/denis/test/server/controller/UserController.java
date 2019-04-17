package com.denis.test.server.controller;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.forms.TokenAndNameForm;
import com.denis.test.server.service.GetTokenServiceImpl;
import com.denis.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class UserController {
    @Autowired
    private UserService service;

    @Value("@{upload.path}")
    private String uploadPath1;

    @Autowired
    private HttpServletRequest request;



    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String addFile(@RequestParam("file") MultipartFile file) {

        if (file!=null){

            String uploadsPath= "C:\\SpringFiles\\";
            File uploadDir = new File(uploadsPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadsPath + resultFilename));
                return "File transfer complete";
            } catch (IOException e) {
                e.printStackTrace();
                return "File transfer error";
            }
        }
        return "File exist error";

    }





    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseBody
    public String getToken(@RequestBody UserEntity userEntity){
        return service.getToken(userEntity);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody AuthorizationForm authorizationForm){
        if(service.checkAuthorization(authorizationForm)!=null)
        {
            GetTokenServiceImpl getTokenService = new GetTokenServiceImpl();
            String token = getTokenService.getToken(authorizationForm.getUsername(),
                    authorizationForm.getPassword(),service);
            service.setToken(authorizationForm.getUsername(),token);
            return token;
        }
        return "";
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity authentication(@RequestBody TokenAndNameForm tokenAndNameForm){
        UserEntity userEntity = service.findByUsername(tokenAndNameForm.getUsername());
        if (userEntity.getToken().equals(tokenAndNameForm.getToken())){
            return  userEntity;
        }
        return null;
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
