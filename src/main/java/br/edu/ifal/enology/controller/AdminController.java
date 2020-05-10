package br.edu.ifal.enology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.TurmaRepository;
import br.edu.ifal.enology.service.ConteudoService;
import br.edu.ifal.enology.service.PalavraService;
import br.edu.ifal.enology.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AdminController {

    @Autowired
    PalavraService palavraService;
    @Autowired
    ConteudoService conteudoService;
    @Autowired
    TurmaRepository turmaRepository;
    @Autowired
    UsuarioService usuarioService;

    @RequestMapping("/adminPage")
    public ModelAndView adminPage(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("admin");
        model.addObject("usuario", usuarioLogado);
        return model;
    }

    @RequestMapping("/alunos")
    public ModelAndView pageGrupo(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("listAlunos");

        model.addObject("usuarios", usuarioService.findAll()).addObject("usuario", usuarioLogado);
        return model;
    }

    @RequestMapping("/tarefa")
    public ModelAndView cadastro_tarefa(Palavra palavra, @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/tarefas");
        Iterable<Palavra> palavras = palavraService.findAll();
        Iterable<Conteudo> conteudos = conteudoService.findAll();

        model.addObject("palavras", palavras).addObject("conteudos", conteudos).addObject("usuario", usuarioLogado);
        return model;
    }

    @GetMapping(value="/turma")
    public ModelAndView criarTurma(@AuthenticationPrincipal Usuario usuario) {
        ModelAndView model = new ModelAndView("turma/turma-add");
        model.addObject("usuario", usuario);
        return model;
    }
    
}