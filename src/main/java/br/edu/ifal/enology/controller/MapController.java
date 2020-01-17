package br.edu.ifal.enology.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MapController{

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa(Authentication authentication) {
        ModelAndView model = new ModelAndView("map/mapa");

        model.addObject("usuario", authentication.getPrincipal());
        return model;
    }
}