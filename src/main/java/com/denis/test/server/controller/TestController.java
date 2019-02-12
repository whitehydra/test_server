package com.denis.test.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/controller")
public class TestController {
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public String getTestStart(ModelMap model){
        return "Hello";
    }
}
