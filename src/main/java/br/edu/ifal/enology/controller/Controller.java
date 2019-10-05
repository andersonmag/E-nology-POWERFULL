package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller{

    @RequestMapping("/")
    public ModelAndView index(){

        ModelAndView model = new ModelAndView("index");
        return model;
        
    }

}