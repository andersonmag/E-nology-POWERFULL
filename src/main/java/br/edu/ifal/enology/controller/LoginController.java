package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LoginController {

    @RequestMapping("/")
    public ModelAndView index() {

        return new ModelAndView("index.html");
    }

    @GetMapping("login")
    public ModelAndView login() {

        return new ModelAndView("user/login.html");
    }
}
