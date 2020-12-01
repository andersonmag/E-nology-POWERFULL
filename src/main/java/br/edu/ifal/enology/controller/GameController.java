package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifal.enology.model.Usuario;

@RestController
@RequestMapping("/games")
public class GameController {

    @RequestMapping("/yamato-s-future")
    public ModelAndView jogarYamatosFuture(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model) {
        model.setViewName("games/yamato");
        model.addObject("usuario", usuarioLogado);

        return model;
    }

    @RequestMapping("/mingle-ng")
    public ModelAndView acessarMingleNG(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model) {
        model.setViewName("games/mingle_index");
        model.addObject("usuario", usuarioLogado);

        return model;
    }

    @RequestMapping("/mingle-ng/play")
    public ModelAndView jogarMingleNG(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model) {
        model.setViewName("games/mingle_game");
        model.addObject("usuario", usuarioLogado);

        return model;
    }
}