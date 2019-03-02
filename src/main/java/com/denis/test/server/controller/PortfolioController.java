package com.denis.test.server.controller;
import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PortfolioController {
    @Autowired
    private PortfolioService service;

    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PortfolioEntity getPortfolio(@PathVariable("id") int userID){
        return service.getById(userID); }

    @RequestMapping(value = "/portfolio", method = RequestMethod.POST)
    @ResponseBody
    public PortfolioEntity addPortfolio(@RequestBody PortfolioEntity portfolioEntity){
        return service.save(portfolioEntity);
    }

    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void deletePortfolio(@PathVariable int id){
        service.remove(id);
    }
}
