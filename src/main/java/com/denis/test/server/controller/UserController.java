package com.denis.test.server.controller;
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



    @RequestMapping(value = "/avatars/{username:.+}", method = RequestMethod.GET)
    public void getAvatar(HttpServletResponse response,
                                    @PathVariable("username") String username) throws IOException {
        UserEntity userEntity = service.findByUsername(username);
        File file = new File(Constants.URL.UPLOADS + userEntity.getAvatar());
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
                File uploadDir = new File(Constants.URL.UPLOADS);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }

                String uuidFile = username + "_" + UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                try {
                    file.transferTo(new File(Constants.URL.UPLOADS + resultFilename));

                 //   File fileToDelete = new File(Constants.URL.UPLOADS + userEntity.getAvatar());
                  //  fileToDelete.delete();

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

}
