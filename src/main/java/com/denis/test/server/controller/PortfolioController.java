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




    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public Set<PortfolioCategoryEntity> getCategory(){
        return portfolioService.getCategoriesList();}

    @RequestMapping(value = "/criteria/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Set<PortfolioCriterionEntity> getCriteions(@PathVariable("id") int id){
        return portfolioService.getCriteriaListByCategoryId(id);}

    @RequestMapping(value = "/types/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Set<PortfolioTypeEntity> getTypes(@PathVariable("id") int id){
        return portfolioService.getTypesListByCategoryId(id);}




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

        PortfolioCategoryEntity ca1, ca2, ca3;
        PortfolioCriterionEntity cr1, cr2, cr3;
        PortfolioTypeEntity tp1, tp2, tp3;

        ca1 = new PortfolioCategoryEntity("Первая категория",1);
        ca2 = new PortfolioCategoryEntity("Вторая категория",2);
        ca3 = new PortfolioCategoryEntity("Третья категория",1);

        cr1 = new PortfolioCriterionEntity("Первый критерий",1);
        cr2 = new PortfolioCriterionEntity("Второй критерий",2);
        cr3 = new PortfolioCriterionEntity("Третий критерий",1);

        tp1 = new PortfolioTypeEntity("Первый тип",1);
        tp2 = new PortfolioTypeEntity("Второй тип",2);
        tp3 = new PortfolioTypeEntity("Третий тип",1);


        portfolioService.saveCategories(Arrays.asList(ca1,ca2,ca3));
        portfolioService.saveCriteria(Arrays.asList(cr1,cr2,cr3));
        portfolioService.saveTypes(Arrays.asList(tp1,tp2,tp3));


        portfolioService.addCriteria(ca1,new HashSet<>(Arrays.asList(cr1,cr2)));
        portfolioService.addCriteria(ca2,new HashSet<>(Arrays.asList(cr3)));
        portfolioService.addCriteria(ca3,new HashSet<>(Arrays.asList(cr1,cr3)));

        portfolioService.addTypes(ca1, new HashSet<>(Arrays.asList(tp1,tp2)));
        portfolioService.addTypes(ca2, new HashSet<>(Arrays.asList(tp2)));
        portfolioService.addTypes(ca3, new HashSet<>(Arrays.asList(tp1,tp3)));


        return "Data initialized";
    }
}
