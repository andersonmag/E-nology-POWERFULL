package br.edu.ifal.enology.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Usuario;

@RestController
public class MapController {

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("map/mapa");

        model.addObject("usuario", usuarioLogado);
        return model;
    }
}