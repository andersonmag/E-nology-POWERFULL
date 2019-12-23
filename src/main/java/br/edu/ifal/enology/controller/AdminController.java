package br.edu.ifal.enology.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.TipoTarefa;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.TarefaRepository;

@RestController
public class AdminController {

    @Autowired
    PalavraRepository palavraRepository;
    @Autowired
    TarefaRepository tarefaRepository;

    @RequestMapping("/cadastro_tarefa")
    public ModelAndView cadastro_tarefa(Tarefa tarefa) {

        ModelAndView model = new ModelAndView("user/tarefas");

        // Iterable<Palavra> palavras = palavraRepository.findAll();
        // model.addObject("palavras", palavras);

        return model;
    }

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar(Palavra palavra, Tarefa tarefa) {

        if (palavra != null) {
            palavraRepository.save(palavra);
        } else {
            tarefaRepository.save(tarefa);
        }

        return new ModelAndView("redirect:/cadastro_tarefa");
    }

    @RequestMapping("/buscar")
    public ModelAndView buscar(String q, RedirectAttributes redirect) {
        ModelAndView model = new ModelAndView("redirect:/cadastro_tarefa");
        List<Palavra> palavras = palavraRepository.findByInglesContaining(q);
        model.addObject("palavras", palavras);
        redirect.addFlashAttribute("palavras", palavras);

        return model;
    }

}