package br.edu.ifal.enology.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.model.Solucao;
import br.edu.ifal.enology.repository.ConteudoRepository;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.TarefaRepository;
import br.edu.ifal.enology.repository.SolucaoRepository;
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
    SolucaoRepository solucaoRepository;
    @Autowired
    SequenciadorService sequenciadorService;

    Tarefa tarefa;
    Long resposta;
    List<Palavra> palavrasEncontradas;

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
    public ModelAndView corrigirResposta(Long palavra, RedirectAttributes redirect, HttpServletRequest request) {
        Usuario usuarioLogado = (Usuario) request.getSession().getAttribute("usuarioLogado");
        resposta = palavra;
        boolean acertou = tarefa.getResposta().getId().equals(palavra);

        if (acertou) {
            usuarioLogado.setPontuacaoDoAluno(usuarioLogado.getPontuacaoDoAluno() + tarefa.getPontuacao());
        }

        Solucao solucao = new Solucao();
        solucao.setAluno(usuarioLogado);
        solucao.setResposta(tarefa.getResposta().getIngles());
        solucao.setAcertou(acertou);
        solucao.setTarefa(tarefa);
        solucao.setPontuacao(tarefa.getPontuacao());
        solucaoRepository.save(solucao);

        redirect.addFlashAttribute("resposta", palavra);
        return new ModelAndView("redirect:/licao/condicionais");
    }

    @RequestMapping("/condicionais")
    public ModelAndView licao(@AuthenticationPrincipal Usuario usuarioLogado) {
        ModelAndView model = new ModelAndView("task/licao1");

        try {
            if (resposta == null) {
                tarefa = sequenciadorService.buscarTarefa(usuarioLogado);
                    System.err.println("oieee");
              palavrasEncontradas = sequenciadorService.buscarPalavrasPorConteudo(
                        sequenciadorService.pegarConteudoAleatorio(tarefa.getResposta()), tarefa.getResposta());
            }
            resposta = null;

            model.addObject("tarefa", tarefa).addObject("palavras", palavrasEncontradas)
                    .addObject("usuario", usuarioLogado).addObject("tarefasTotal", tarefaRepository.findAll().size())
                    .addObject("tarefasAtual", solucaoRepository.findByAluno(usuarioLogado).size() + 1);
        } catch (NullPointerException e) {
            model.setViewName("task/resultado");
            model.addObject("usuario", usuarioLogado);
        }
        return model;
    }
}
