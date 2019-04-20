package com.denis.test.server.controller;
import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.entity.PortfolioFileEntity;
import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.service.PortfolioService;
import com.denis.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;

    private boolean userVerification(List<Map<String,Object>> allParams){
        if (allParams.isEmpty())return false;
        String username = allParams.get(0).get("username").toString();
        String token = allParams.get(0).get("token").toString();
        if(username == null || token == null) return false;

        UserEntity userEntity = userService.findByUsername(username);
        return userEntity.getToken().equals(token);
    }











    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PortfolioEntity getPortfolio(@PathVariable("id") int portfolioID){
        return portfolioService.getById(portfolioID); }


    @RequestMapping(value = "/pf/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<PortfolioEntity> getPortfolio(@PathVariable("username") String username){
        return portfolioService.getListByUsername(username);}







    @RequestMapping(value = "/portfolio/files", method = RequestMethod.POST)
    @ResponseBody
    public String getFiles(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            PortfolioFileEntity file = PortfolioFileEntity.CreateObjectFromMap(allParams.get(1));
            portfolioService.addFileToPortfolio(file);
            return "Done";
        }
        return "Error";
    }


    @RequestMapping(value = "/portfolio/add", method = RequestMethod.POST)
    @ResponseBody
    public PortfolioEntity addPortfolio(
            @RequestBody List<Map<String,Object>> allParams){

        if( userVerification(allParams)) {
            UserEntity author = userService.findByUsername(allParams.get(0).get("username").toString());
            PortfolioEntity portfolioEntity = PortfolioEntity.CreateObjectFromMap(allParams.get(1));
            if(portfolioEntity!=null){
                portfolioEntity.setAuthor(author);



                return portfolioService.save(portfolioEntity);

               // return null;
            }
        } return null;
    }




    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deletePortfolio(@PathVariable int id){
        portfolioService.remove(id);
    }




    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public String initData(){

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setName("Фадеев Денис");
        userEntity.setPassword("admin");
        userEntity.setLevel("Студент");
        userEntity.setFaculty("ФИТ");
        userEntity.setStudyGroup("15ИВТ-1");
        userEntity.setPhone("+79529616552");
        userEntity.setMail("whitehydra@yandex.ru");
        userEntity.setInfo("Пробная страница");
        userService.save(userEntity);


        return "Data initialized";
    }
}
