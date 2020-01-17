package br.edu.ifal.enology.controller;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.ConteudoRepository;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.TarefaRepository;
import br.edu.ifal.enology.service.SequenciadorService;

@RequestMapping("/licao")
@RestController
public class TaskController {

    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    PalavraRepository palavraRepository;
    @Autowired
    ConteudoRepository conteudoRepository;
    @Autowired
    SequenciadorService sequenciadorService;
    Tarefa tarefa;
    Usuario usuarioLogado;

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar(Palavra palavra, Tarefa tarefa) {
        if (palavra.getIngles() != null) {
            palavraRepository.save(palavra);
        } else {
            tarefaRepository.save(tarefa);
        }

        return new ModelAndView("redirect:/tarefa");
    }

    @RequestMapping("/corrigir")
    public ModelAndView corrigirResposta(String palavra) {
        ModelAndView model = new ModelAndView("redirect:/licao/condicionais");

        if (tarefa.getResposta().getIngles().equals(palavra)) {
            usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + tarefa.getPontuacao());

            return model;
        }
        return model;
    }

    @RequestMapping("/condicionais")
    public ModelAndView licao(Authentication authentication) {
        ModelAndView model = new ModelAndView("task/licao1");
        usuarioLogado = (Usuario) authentication.getPrincipal();
        Random random = new Random();
        List<Tarefa> tarefas = tarefaRepository.findAll();
        int indexSorteio = random.nextInt(tarefas.size());
        tarefa = tarefas.get(indexSorteio);
        List<Palavra> palavrasEncontradas = sequenciadorService.buscarPalavrasPorConteudo("condicionais",
                tarefa.getResposta().getIngles());

        model.addObject("tarefa", tarefa).addObject("palavras", palavrasEncontradas).addObject("usuario",
                usuarioLogado);
        return model;
    }
}