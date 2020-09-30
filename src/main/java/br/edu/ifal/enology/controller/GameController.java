package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class GameController {

    @RequestMapping("/games/yamato-s-future")
    public ModelAndView jogarYamatosFuture() {
        return new ModelAndView("games/yamato");
    }
}