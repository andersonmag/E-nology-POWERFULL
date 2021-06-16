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
import br.edu.ifal.enology.service.*;

@RequestMapping("/studies")
@RestController
public class TaskController {

    @Autowired
    private SequenciadorService sequenciadorService;
    @Autowired
    private PalavraService palavraService;
    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private SolucaoService solucaoService;
    @Autowired
    private ConteudoService conteudoService;

    @RequestMapping("/cadastrar")
    public ModelAndView cadastrar(Palavra palavra, Tarefa tarefa) {
        if (palavra.getIngles() != null) {
            palavraService.save(palavra);
        } else {
            tarefa.setConteudo(tarefa.getResposta().getConteudos().get(0));
            tarefaService.save(tarefa);
        }

        return new ModelAndView("redirect:/tarefa");
    }

    @RequestMapping("/corrigir/{id}/{contId}")
    public ModelAndView corrigirResposta(@PathVariable("id") Long idTarefa, @PathVariable("contId") Long contId,
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
            pontuacaoResposta = usuarioLogado.getPontuacaoDoAluno() - 5;
            usuarioLogado.setPontuacaoDoAluno(pontuacaoResposta);
            usuarioService.save(usuarioLogado);
        }

        solucao.setAluno(usuarioLogado);
        solucao.setResposta(tarefaAtual.getResposta().getIngles());
        solucao.setAcertou(acertou);
        solucao.setTarefa(tarefaAtual);
        solucao.setPontuacao(pontuacaoResposta);
        solucaoService.save(solucao);

        Conteudo conteudo = conteudoService.findById(contId);
        List<Solucao> solucoesDoAluno = solucaoService.buscarPorUsuario(usuarioLogado);
        List<Tarefa> tarefasRespondidasCorretamente = sequenciadorService
                .filtrarTarefasRespondidas(solucoesDoAluno);
        List<Tarefa> tarefasConteudo = tarefaService.buscarPorConteudo(conteudo);

        boolean concluiu = sequenciadorService
        .verificarConclusaoConteudo(tarefasRespondidasCorretamente, tarefasConteudo);

        if(concluiu) {
            solucao.getTarefa().getConteudo().setPraticado(concluiu);
            usuarioLogado.setFaseAtual(usuarioLogado.getFaseAtual() + 1);

            solucao.setAluno(usuarioLogado);
            usuarioService.save(usuarioLogado);
            solucaoService.save(solucao);
        }

        redirect.addFlashAttribute("pontuacaoNaLIcao", pontuacaoResposta);
        return new ModelAndView("redirect:/studies/" + contId + "/practice");
    }

    @RequestMapping("/{id}/practice")
    public ModelAndView licao(@AuthenticationPrincipal Usuario usuarioLogado,
            @CookieValue(defaultValue = "", name = "user") String userAcess, HttpServletResponse response,
            @PathVariable("id") String contId) {
        ModelAndView model = new ModelAndView("task/licao1");

        Conteudo conteudo = conteudoService.findById(Long.parseLong(contId));
        List<Solucao> solucoesDoAluno = solucaoService.buscarPorUsuario(usuarioLogado);
        List<Tarefa> tarefasRespondidasCorretamente = sequenciadorService
                .filtrarTarefasRespondidas(solucoesDoAluno);
        List<Tarefa> tarefasConteudo = tarefaService.buscarPorConteudo(conteudo);

        if (conteudo == null) {
            model.setViewName("redirect:/mapa");
            return model;
        }

        boolean isProximo = sequenciadorService.isProximoConteudo(conteudo, tarefasRespondidasCorretamente,
                tarefasConteudo);

        if (!isProximo) {
            model.setViewName("redirect:/mapa");
            return model;
        }

        if (userAcess.equals("")) {
            Cookie cookie = new Cookie("user", "again");
            response.addCookie(cookie);
            model.setViewName("task/attention");
            model.addObject("conteudo", contId);
            return model;
        }

        try {
            Tarefa tarefa = sequenciadorService.buscarTarefa(usuarioLogado, conteudo);
            List<Palavra> palavrasEncontradas = sequenciadorService.buscarPalavrasPorConteudo(
                    sequenciadorService.pegarConteudoAleatorio(tarefa.getResposta()), tarefa.getResposta());

            model.addObject("tarefa", tarefa).addObject("palavras", palavrasEncontradas)
                    .addObject("usuario", usuarioLogado).addObject("tarefasConteudo", tarefasConteudo.size())
                    .addObject("tarefasRespondidasAtualmente", sequenciadorService.
                                    filtrarTarefasRespondidasPorConteudo(solucoesDoAluno, conteudo).size());

        } catch (NullPointerException e) {
            model.setViewName("task/resultado");
            model.addObject("usuario", usuarioLogado);
        }
        return model;
    }

}
