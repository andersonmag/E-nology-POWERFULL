package br.edu.ifal.enology.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.Texto;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.UserRepository;
import br.edu.ifal.enology.service.ConteudoService;
import br.edu.ifal.enology.service.PalavraService;
import br.edu.ifal.enology.service.TarefaService;
import br.edu.ifal.enology.service.UsuarioService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    PalavraService palavraService;
    @Autowired
    ConteudoService conteudoService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TarefaService tarefaService;
    @Autowired
    private PalavraRepository palavraRepository;

    @RequestMapping
    public ModelAndView adminPage(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("admin");
        model.addObject("usuario", usuarioLogado);
        return model;
    }

    @RequestMapping("/alunos")
    public ModelAndView pegarLista(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("listAlunos");

        model.addObject("usuarios", usuarioService.findAll()).addObject("usuario", usuarioLogado);
        return model;
    }

    @RequestMapping("/sistema/criar-conteudo")
    public ModelAndView cadastro_conteudo(Conteudo conteudo, @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("montar-fases");
        Iterable<Conteudo> conteudos = conteudoService.findAll();
        List<Conteudo> conteudosNovaOrdem = new ArrayList<>();

        model.addObject("conteudos", conteudos).addObject("usuario", usuarioLogado).addObject("conteudosNovaOrdem", conteudosNovaOrdem);
        return model;
    }

    @RequestMapping("/sistema/criar-tarefa")
    public ModelAndView cadastro_tarefa(Conteudo conteudo, Tarefa tarefa, Texto texto, Palavra palavra, @RequestParam(required = false) Long idConteudo , @AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("user/tarefas");
        tarefa.setTexto(texto);
        Iterable<Conteudo> conteudos = conteudoService.findAll();
        Iterable<Tarefa> tarefas = null;
        Iterable<Palavra> palavras = null;

        if(idConteudo == null) {
            tarefas = tarefaService.findAll();
            palavras = palavraRepository.findAll();
        } else {
            conteudo = conteudoService.findById(idConteudo);
                if(Optional.of(conteudo).isPresent()) {
                    tarefas = tarefaService.buscarPorConteudo(conteudo);
                    palavras = palavraRepository.findByConteudos(conteudo);
                }
        }

        model.addObject("palavras", palavras).addObject("conteudos", conteudos).addObject("usuario", usuarioLogado)
                .addObject("conteudo", conteudo).addObject("tarefas", tarefas).addObject("tarefa", tarefa);
        return model;
    }
}