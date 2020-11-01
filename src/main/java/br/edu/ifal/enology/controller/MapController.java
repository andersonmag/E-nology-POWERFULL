package br.edu.ifal.enology.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.service.ConteudoService;
import br.edu.ifal.enology.service.SolucaoService;

@RestController
public class MapController {

    @Autowired
    private SolucaoService solucaoService;

    @RequestMapping("/mapa")
    public ModelAndView mostrarMapa(@AuthenticationPrincipal Usuario usuarioLogado, HttpServletRequest request) {
        ModelAndView model = new ModelAndView("map/mapa");

        List<Conteudo> conteudos = solucaoService.buscarConteudosPraticadosPorUsuario(usuarioLogado);

        if (request.isUserInRole("ROLE_ADMIN")) {
            model.setViewName("redirect:/admin");
            return model;
        }

        model.addObject("usuario", usuarioLogado)
             .addObject("conteudo_atual", conteudos.size());
        return model;
    }
}