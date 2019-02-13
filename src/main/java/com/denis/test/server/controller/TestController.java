package com.denis.test.server.controller;

import com.denis.test.server.entity.FirstEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/controller")                //url path контроллера
public class TestController {
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public FirstEntity getTestStart(){

        return createEmptyEntity();
    }

    private FirstEntity createEmptyEntity() {
        FirstEntity firstEntity = new FirstEntity();
        firstEntity.setId(1);
        firstEntity.setTitle("First entity");
        firstEntity.setInfo("Hello!");
        return firstEntity;
    }
}
