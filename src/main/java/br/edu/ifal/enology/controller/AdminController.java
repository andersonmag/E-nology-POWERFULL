package br.edu.ifal.enology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.ConteudoRepository;
import br.edu.ifal.enology.repository.PalavraRepository;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AdminController {

    @Autowired
    PalavraRepository palavraRepository;
    @Autowired
    ConteudoRepository conteudoRepository;

    @RequestMapping("/attention")
    public ModelAndView att() {
        ModelAndView model = new ModelAndView("task/attention");
        return model;
    }

    @RequestMapping("/tarefa")
    public ModelAndView cadastro_tarefa(Palavra palavra, @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/tarefas");
        Iterable<Palavra> palavras = palavraRepository.findAll();
        Iterable<Conteudo> conteudos = conteudoRepository.findAll();

        model.addObject("palavras", palavras).addObject("conteudos", conteudos).addObject("usuario", usuarioLogado);
        return model;
    }

    @RequestMapping("/about")
    public ModelAndView about() {
        return new ModelAndView("about");
    }

}