package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ControllerLogin {
    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index.html");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("user/login.html");
    }
}
