package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TaskController{

    @RequestMapping("/licao")
    public ModelAndView licao() {
        return new ModelAndView("task/licao1");
    }

    @RequestMapping("/licao2")
    public ModelAndView licao2() {
        return new ModelAndView("task/licao2.html");
    }

    @RequestMapping("/licao3")
    public ModelAndView licao3() {
        return new ModelAndView("task/licao3.html");
    }

}