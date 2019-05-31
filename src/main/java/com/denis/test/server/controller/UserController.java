package com.denis.test.server.controller;
import com.denis.test.server.entity.FacultyEntity;
import com.denis.test.server.entity.GroupEntity;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.forms.AuthorizationForm;
import com.denis.test.server.forms.TokenAndNameForm;
import com.denis.test.server.other.Constants;
import com.denis.test.server.other.Functions;
import com.denis.test.server.service.GetTokenServiceImpl;
import com.denis.test.server.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
public class UserController {
    @Autowired
    private UserService service;

    @Value("@{upload.path}")
    private String uploadPath1;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ServletContext context;


    private boolean userVerification(List<Map<String,Object>> allParams){
        if (allParams.isEmpty())return false;
        String username = allParams.get(0).get("username").toString();
        String token = allParams.get(0).get("token").toString();

        return userVerification(username, token);
    }

    private boolean userVerification(String username, String token){
        if(username == null || token == null) return false;
        UserEntity userEntity = service.findByUsername(username);
        return userEntity.getToken().equals(token);
    }



    @RequestMapping(value = "/avatars/{avatar:.+}", method = RequestMethod.GET)
    public void getAvatar(HttpServletResponse response,
                                    @PathVariable("avatar") String avatar) throws IOException {
        File file = new File(Constants.URL.UPLOADS_AVATARS + avatar);
        InputStream in = new FileInputStream(file);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String fileUpload(@RequestParam String username,
                          @RequestParam String token, @RequestParam("file") MultipartFile file) {

        if (file!=null){
            UserEntity userEntity = service.findByUsername(username);
            if (userEntity.getToken().equals(token)){
                File uploadDir = new File(Constants.URL.UPLOADS_AVATARS);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String uuidFile = username + "_" + UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                try {

                    file.transferTo(new File(Constants.URL.UPLOADS_AVATARS + resultFilename));

                    service.setAvatar(username,resultFilename);
                    return "Done " + resultFilename;
                } catch (IOException e) {
                    e.printStackTrace();
                    return "File transfer error";
                }
            }
        }
        return "File exist error";

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



    @RequestMapping(value = "/faculties", method = RequestMethod.GET)
    @ResponseBody
    public List<FacultyEntity> getFaculties(){
        return Functions.removeDublicates(service.getFacultiesList());}

    @RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupEntity> getGroups(@PathVariable("id") int id){
        if(id == -1)return Functions.removeDublicates(service.getGroupsList());
        return Functions.removeDublicates(service.getGroupListByFacultyId(id));
    }




    @RequestMapping(value = "/users/update", method = RequestMethod.GET)
    @ResponseBody
    public String updateHash(){
        List<UserEntity> users = service.getAll();
        int z = 0;
        for (UserEntity user : users) {
            if (user.getPassword().length() <= 32 || user.getAvatar() == null) {
                service.save(user);
                z++;
            }
        }
        if(z == 0)return "Пароли пользователей уже захешированы";
        return "Захешировано паролей - " + z;
    }





    @RequestMapping(value = "/pin/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Boolean getPinStatus(@PathVariable("username") String username){
        UserEntity user = service.findByUsername(username);
        return user.getPin() == null;
    }

    @RequestMapping(value = "/pin/check", method = RequestMethod.POST)
    @ResponseBody
    public Boolean checkPin(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            String pinText = allParams.get(1).get("pin").toString();
            UserEntity user = service.findByUsername(allParams.get(0).get("username").toString());
            if(user!=null){
                if(user.getPin().equals(pinText))return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/pin/set", method = RequestMethod.POST)
    @ResponseBody
    public Boolean setPin(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            String pinText = allParams.get(1).get("pin").toString();
            UserEntity user = service.findByUsername(allParams.get(0).get("username").toString());
            if(user!=null){
                user.setPin(pinText);
                service.update(user);
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/edit/profile", method = RequestMethod.POST)
    @ResponseBody
    public Boolean editProfile(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            String phone = allParams.get(1).get("phone").toString();
            String mail = allParams.get(1).get("mail").toString();
            String info = allParams.get(1).get("info").toString();
            UserEntity user = service.findByUsername(allParams.get(0).get("username").toString());
            user.setPhone(phone);
            user.setMail(mail);
            user.setInfo(info);
            service.update(user);
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/edit/password", method = RequestMethod.POST)
    @ResponseBody
    public Boolean editPassword(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            String oldPassword = allParams.get(1).get("oldPassword").toString();
            String newPassword = allParams.get(1).get("newPassword").toString();
            UserEntity user = service.findByUsername(allParams.get(0).get("username").toString());
            if(user.getPassword().equals(oldPassword)){
                user.setPassword(newPassword);
                service.update(user);
                return true;
            }
        }
        return false;
    }


    @RequestMapping(value = "/users/get", method = RequestMethod.POST)
    @ResponseBody
    public List<UserEntity> getAllUsers(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            return service.getAll();
        }
        return null;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserEntity getUser(@PathVariable("id") int userID){
        return service.getById(userID);
    }




    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public UserEntity addUser(@RequestBody List<Map<String,Object>> allParams){
        FacultyEntity faculty = service.getFacultyById((int)allParams.get(0).get("id_faculty"));
        GroupEntity group = service.getGroupById((int)allParams.get(0).get("id_group"));
        UserEntity userEntity = UserEntity.CreateObjectFromMap(allParams.get(0));
        if(userEntity!=null){
            userEntity.setFaculty(faculty);
            userEntity.setGroup(group);
            service.save(userEntity);
            return userEntity;
        }
        return  null;
    }






    @RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(@PathVariable int id){
        service.remove(id);
    }

}
