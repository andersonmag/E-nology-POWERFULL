package br.edu.ifal.enology.controller;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;
import br.edu.ifal.enology.model.*;
import br.edu.ifal.enology.service.*;

@RequestMapping("/exercicio")
@RestController
public class TaskController {

    @Autowired
    SequenciadorService sequenciadorService;
    @Autowired
    PalavraService palavraService;
    @Autowired
    TarefaService tarefaService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    SolucaoService solucaoService;

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar(Palavra palavra, Tarefa tarefa) {
        if (palavra.getIngles() != null) {
            palavraService.save(palavra);
        } else {
            tarefaService.save(tarefa);
        }

        return new ModelAndView("redirect:/tarefa");
    }

    @RequestMapping("/corrigir/{id}")
    public ModelAndView corrigirResposta(@PathVariable("id") Long idTarefa,
            @AuthenticationPrincipal Usuario usuarioLogado, Long palavra, RedirectAttributes redirect) {
        Solucao solucao = new Solucao();
        int pontuacaoResposta;
        Tarefa tarefaAtual = tarefaService.findById(idTarefa);
        boolean acertou = tarefaAtual.getResposta().getId().equals(palavra);
        if (acertou) {
            pontuacaoResposta = usuarioLogado.getPontuacaoDoAluno() + tarefaAtual.getPontuacao();
            usuarioLogado.setPontuacaoDoAluno(pontuacaoResposta);
            usuarioService.save(usuarioLogado);
        } else {
            pontuacaoResposta = tarefaAtual.getPontuacao() / 2;
            tarefaAtual.setPontuacao(pontuacaoResposta);
            tarefaService.save(tarefaAtual);
        }

        solucao.setAluno(usuarioLogado);
        solucao.setResposta(tarefaAtual.getResposta().getIngles());
        solucao.setAcertou(acertou);
        solucao.setTarefa(tarefaAtual);
        solucao.setPontuacao(pontuacaoResposta);
        solucaoService.save(solucao);

        redirect.addFlashAttribute("pontuacaoNaLIcao", pontuacaoResposta);
        return new ModelAndView("redirect:/exercicio/intro");
    }

    @Transactional
    @RequestMapping("/intro")
    public ModelAndView licao(@AuthenticationPrincipal Usuario usuarioLogado,
            @CookieValue(defaultValue = "", name = "user") String userAcess, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("task/licao1");

        if (userAcess.equals("")) {
            Cookie cookie = new Cookie("user", "again");
            response.addCookie(cookie);
            model.setViewName("task/attention");
            return model;
        }

        try {
            Tarefa tarefa = sequenciadorService.buscarTarefa(usuarioLogado);
            List<Palavra> palavrasEncontradas = sequenciadorService.buscarPalavrasPorConteudo(
                    sequenciadorService.pegarConteudoAleatorio(tarefa.getResposta()), tarefa.getResposta());

            model.addObject("tarefa", tarefa).addObject("palavras", palavrasEncontradas)
                    .addObject("usuario", usuarioLogado).addObject("tarefasTotal", tarefaService.findAll().size())
                    .addObject("tarefasRespondidasAtualmente", sequenciadorService
                            .filtrarTarefasRespondidas(solucaoService.findByAluno(usuarioLogado)).size());
        } catch (NullPointerException e) {
            usuarioLogado.setFaseAtual(usuarioLogado.getFaseAtual() + 1);
            usuarioService.save(usuarioLogado);
            model.setViewName("task/resultado");
            model.addObject("usuario", usuarioLogado);
        }
        return model;
    }
}
