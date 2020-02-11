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
import br.edu.ifal.enology.model.*;
import br.edu.ifal.enology.repository.*;
import br.edu.ifal.enology.service.*;

@RequestMapping("/exercicio")
@RestController
public class TaskController {

    @Autowired
    UserRepository userRepository;
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
    @Autowired
    TarefaService tarefaService;

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar(Palavra palavra, Tarefa tarefa) {
        if (palavra.getIngles() != null) {
            palavraRepository.save(palavra);
        } else {
            tarefaRepository.save(tarefa);
        }

        return new ModelAndView("redirect:/tarefa");
    }

    @RequestMapping("/corrigir/{id}")
    public ModelAndView corrigirResposta(@PathVariable("id") Long idTarefa,
            @AuthenticationPrincipal Usuario usuarioLogado, Long palavra, RedirectAttributes redirect) {
        Solucao solucao = new Solucao();
        int pontuacaoResposta;
        Tarefa tarefaAtual = tarefaService.pegarTarefaPorId(idTarefa);
        boolean acertou = tarefaAtual.getResposta().getId().equals(palavra);
        if (acertou) {
            pontuacaoResposta = usuarioLogado.getPontuacaoDoAluno() + tarefaAtual.getPontuacao();
            usuarioLogado.setPontuacaoDoAluno(pontuacaoResposta);
            userRepository.save(usuarioLogado);
        } else {
            pontuacaoResposta = tarefaAtual.getPontuacao() / 2;
            tarefaAtual.setPontuacao(pontuacaoResposta);
            tarefaRepository.save(tarefaAtual);
        }

        solucao.setAluno(usuarioLogado);
        solucao.setResposta(tarefaAtual.getResposta().getIngles());
        solucao.setAcertou(acertou);
        solucao.setTarefa(tarefaAtual);
        solucao.setPontuacao(pontuacaoResposta);
        solucaoRepository.save(solucao);

        redirect.addFlashAttribute("pontuacaoNaLIcao", pontuacaoResposta);
        return new ModelAndView("redirect:/exercicio/intro");
    }

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
                    .addObject("usuario", usuarioLogado).addObject("tarefasTotal", tarefaRepository.findAll().size())
                    .addObject("tarefasRespondidasAtualmente", sequenciadorService
                            .filtrarTarefasRespondidas(solucaoRepository.findByAluno(usuarioLogado)).size());
        } catch (NullPointerException e) {
            model.setViewName("task/resultado");
            model.addObject("usuario", usuarioLogado);
        }
        return model;
    }
}
