package br.edu.ifal.enology.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/games")
public class GameController {

    @RequestMapping("/yamato-s-future")
    public ModelAndView jogarYamatosFuture() {
        return new ModelAndView("games/yamato");
    }

    @RequestMapping("/mingle-ng")
    public ModelAndView acessarMingleNG() {
        return new ModelAndView("games/mingle_index");
    }

    @RequestMapping("/mingle-ng/play")
    public ModelAndView jogarMingleNG() {
        return new ModelAndView("games/mingle_game");
    }

    @RequestMapping("/mingle-ng/rank")
    public ModelAndView verRankMingleNG() {
        return new ModelAndView("games/mingle_rank");
    }
}