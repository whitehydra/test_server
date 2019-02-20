package com.denis.test.server.controller;

import com.denis.test.server.entity.FirstEntity;
import com.denis.test.server.repository.FirstRepository;
import com.denis.test.server.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {


    @Autowired
    private TestService service;


    @RequestMapping(value = "/tests", method = RequestMethod.GET)
    @ResponseBody
    public List<FirstEntity> getAllTest(){

        List<FirstEntity> list = new ArrayList<>();
        list.add(createEmptyEntity());
        return list;

  //      return service.getAll();
    }

    @RequestMapping(value = "/tests/{id}", method = RequestMethod.GET)
    @ResponseBody
    public FirstEntity getTest(@PathVariable("id") long testID){
        return service.getById(testID);
    }

    @RequestMapping(value = "/tests", method = RequestMethod.POST)
    @ResponseBody
    public FirstEntity addTest(@RequestBody FirstEntity firstEntity){
        return service.save(firstEntity);
    }

    @RequestMapping(value = "/tests/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteTest(@PathVariable long id){
        service.remove(id);
    }






    private FirstEntity createEmptyEntity() {
        FirstEntity firstEntity = new FirstEntity();
        firstEntity.setId(1);
        firstEntity.setTitle("First entity");
        firstEntity.setInfo("Hello!");
        return firstEntity;
    }
}
