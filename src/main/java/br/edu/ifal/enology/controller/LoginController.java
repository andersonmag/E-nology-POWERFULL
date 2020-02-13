package br.edu.ifal.enology.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Usuario;

@RestController
public class LoginController {

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/cadastro")
    public ModelAndView mostrarcadastro() {
        return new ModelAndView("user/cadastro");
    }

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("user/login");
    }

    @RequestMapping("/sobre")
    public ModelAndView sobre(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("sobre");
        model.addObject("usuario", usuarioLogado);
        return model;
    }
}
