package com.denis.test.server.controller;
import com.denis.test.server.entity.*;
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
        return portfolioService.getPortfolioById(portfolioID); }


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
    public Integer addPortfolio(
            @RequestBody List<Map<String,Object>> allParams){

        if( userVerification(allParams)) {
      //  if( true) {
            UserEntity author = userService.findByUsername(allParams.get(0).get("username").toString());
            PortfolioCategoryEntity category = portfolioService.getCategoryById((int)allParams.get(1).get("id_category"));
            PortfolioCriterionEntity criterion = portfolioService.getCriterionById((int)allParams.get(1).get("id_criterion"));
            PortfolioTypeEntity type = portfolioService.getTypeById((int)allParams.get(1).get("id_type"));


            PortfolioEntity portfolioEntity = PortfolioEntity.CreateObjectFromMap(allParams.get(1));
            if(portfolioEntity!=null){
                portfolioEntity.setAuthor(author);
                portfolioEntity.setCategory(category);
                portfolioEntity.setCriterion(criterion);
                portfolioEntity.setType(type);
                portfolioService.savePortfolio(portfolioEntity);
                return portfolioEntity.getId_portfolio();
               // return null;
            }
        } return null;
    }




    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deletePortfolio(@PathVariable int id){
        portfolioService.removePortfolio(id);
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

        portfolioService.saveCategory(new PortfolioCategoryEntity("Первая",1));
        portfolioService.saveCategory(new PortfolioCategoryEntity("Вторая",2));
        portfolioService.saveCategory(new PortfolioCategoryEntity("Третья",1));

        portfolioService.saveCriterion(new PortfolioCriterionEntity("Первый",1));
        portfolioService.saveCriterion(new PortfolioCriterionEntity("Второй",2));
        portfolioService.saveCriterion(new PortfolioCriterionEntity("Третий",1));

        portfolioService.saveType(new PortfolioTypeEntity("Первый",1));
        portfolioService.saveType(new PortfolioTypeEntity("Второй",2));
        portfolioService.saveType(new PortfolioTypeEntity("Третий",1));

        return "Data initialized";
    }
}
