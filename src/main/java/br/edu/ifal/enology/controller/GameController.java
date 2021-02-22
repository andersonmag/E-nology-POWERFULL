package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.service.UsuarioService;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/yamato-s-future")
    public ModelAndView jogarYamatosFuture(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model) {
        model.setViewName("games/yamato");
        model.addObject("usuario", usuarioLogado);

        return model;
    }

    @RequestMapping("/yamato-s-future/{option}/check/")
    public ModelAndView pontuarYamatosFuture(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model,
            @PathVariable("option") String option) {
        if (option.equals("exit")) {
            model.setViewName("redirect:/");
        } else {
            model.setViewName("redirect:/games/yamato-s-future");
        }
        
        String miniGame = "yamato";

        usuarioService.pontuarPorMiniGame(usuarioLogado, miniGame);
        model.addObject("usuario", usuarioLogado);

        return model;
    }

    @RequestMapping("/mingle/{option}/check/")
    public ModelAndView pontuarMingle(@AuthenticationPrincipal Usuario usuarioLogado, ModelAndView model,
            @PathVariable("option") String option) {
        if (option.equals("exit")) {
            model.setViewName("redirect:/");
        } else {
            model.setViewName("redirect:/games/mingle-ng/play");
        }

        String miniGame = "mingle";

        usuarioService.pontuarPorMiniGame(usuarioLogado, miniGame);
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