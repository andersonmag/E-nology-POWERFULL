package br.edu.ifal.enology.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.*;
import br.edu.ifal.enology.repository.*;

@Service
public class SequenciadorService {

    @Autowired
    private PalavraRepository palavraRepository;
    @Autowired
    private TarefaService tarefaService;
    @Autowired
    private SolucaoService solucaoService;
    @Autowired
    private ConteudoService conteudoService;

    public boolean verificarConclusaoConteudo(List<Tarefa> tarefasRespondidasCorretamente,
            List<Tarefa> tarefasConteudo) {
        return tarefasRespondidasCorretamente.containsAll(tarefasConteudo);
    }

    public boolean verificarSolucaoExistenteComConteudo(Conteudo conteudo,
            List<Tarefa> tarefasRespondidasCorretamente) {
        return tarefasRespondidasCorretamente.stream()
                .anyMatch(tarefa -> tarefa.getConteudo().equals(conteudo));
    }

    public boolean isProximoConteudo(Conteudo conteudo, List<Tarefa> tarefasRespondidasCorretamente, List<Tarefa> tarefasConteudo) {

        if(tarefasRespondidasCorretamente.isEmpty() && conteudo.getId() == 1L) {
            return true;
        }

        else if(verificarSolucaoExistenteComConteudo(conteudo, tarefasRespondidasCorretamente))
            return true;

        Long proximoConteudo = conteudo.getId();
        
        Long conteudoInicial = 1L;
        Long maiorConteudo = conteudoInicial;

        for (Tarefa tarefa : tarefasRespondidasCorretamente) {
            Long proximoLista = conteudoInicial;

            proximoLista = tarefa.getConteudo().getId();

            if (proximoLista > maiorConteudo) {
                maiorConteudo = proximoLista;
            }
        }
        boolean anteriorJaPraticado = conteudoService.findById(maiorConteudo).isPraticado();
        maiorConteudo ++;
        boolean proximoSolicitado = maiorConteudo == proximoConteudo;

        boolean isProximo = anteriorJaPraticado
                && proximoSolicitado;

        return isProximo;
    }

    public Tarefa buscarTarefa(Usuario usuario, Conteudo conteudo) {
        List<Solucao> solucoesDoAluno = solucaoService.buscarPorUsuario(usuario);
        List<Tarefa> tarefasRespondidasCorretamente = filtrarTarefasRespondidas(solucoesDoAluno);
        List<Tarefa> tarefasConteudo = tarefaService.buscarPorConteudo(conteudo);

        if (solucoesDoAluno.isEmpty()) {
            if (!conteudo.getId().equals(1L)) {
                conteudo = conteudoService.findById(1L);
                tarefasConteudo = tarefaService.buscarPorConteudo(conteudo);
            }
        }
        
        return selecionarTarefa(tarefasConteudo, tarefasRespondidasCorretamente);
    }

    public List<Tarefa> filtrarTarefasRespondidas(List<Solucao> solucoesDoAlunoDoConteudo) {
        List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

        for (Solucao solucao : solucoesDoAlunoDoConteudo) {
            if (solucao.isAcertou())
                tarefasRespondidasCorretamente.add(solucao.getTarefa());
        }
        return tarefasRespondidasCorretamente;
    }

    public List<Tarefa> filtrarTarefasRespondidasPorConteudo(List<Solucao> solucoesDoAlunoDoConteudo, Conteudo conteudo) {
        List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

        for (Solucao solucao : solucoesDoAlunoDoConteudo) {
            if (solucao.isAcertou() && 
                    solucao.getTarefa().getConteudo().equals(conteudo))
                tarefasRespondidasCorretamente.add(solucao.getTarefa());
        }
        return tarefasRespondidasCorretamente;
    }


    private Tarefa selecionarTarefa(List<Tarefa> tarefasConteudo, List<Tarefa> tarefasRespondidasCorretamente) {
        List<Tarefa> tarefasRestantesTexto = new ArrayList<>();
        List<Tarefa> tarefasRestantesGeral = new ArrayList<>();

        Random numeroAleatorio = new Random();
        int indexTarefa = 0;

        for (Tarefa tarefa : tarefasConteudo) {
                if (tarefasRespondidasCorretamente.isEmpty() || !tarefasRespondidasCorretamente.contains(tarefa)) {
                    if (tarefa.getTipoTarefa() == TipoTarefa.MULTIPLA_ESCOLHA_TEXTO) {
                        tarefasRestantesTexto.add(tarefa);
                    } else {
                        tarefasRestantesGeral.add(tarefa);
                    }
                }
            }

        if (verificarConclusaoConteudo(tarefasRespondidasCorretamente, tarefasConteudo))
            return null;

        if (tarefasRestantesTexto.size() > 0) {
            indexTarefa = numeroAleatorio.nextInt(tarefasRestantesTexto.size());
            return tarefasRestantesTexto.get(indexTarefa);
        } else {
            indexTarefa = numeroAleatorio.nextInt(tarefasRestantesGeral.size());
            return tarefasRestantesGeral.get(indexTarefa);
        }
    }

    public Conteudo pegarConteudoAleatorio(Palavra respostaDaTarefa) {
        Random numeroAleatorio = new Random();
        List<Conteudo> todosConteudos = conteudoService.findAll();
        int indexConteudo;

        for (Conteudo conteudo : todosConteudos) {
            if (respostaDaTarefa.getConteudos().contains(conteudo)) {
                return conteudo;
            }
        }
        indexConteudo = numeroAleatorio.nextInt(todosConteudos.size());

        return todosConteudos.get(indexConteudo);
    }

    public List<Palavra> buscarPalavrasPorConteudo(Conteudo conteudo, Palavra respostaDaTarefa) {
        List<Palavra> palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
        List<Palavra> palavrasComTamanhoCorreto = new ArrayList<>();
        int tamanhoMaximoDaLista = 3;

        Collections.shuffle(palavrasFiltradasPorConteudo);

        for (int i = 0; i < palavrasFiltradasPorConteudo.size(); i++) {
            if (palavrasComTamanhoCorreto.size() < tamanhoMaximoDaLista
                    && palavrasFiltradasPorConteudo.get(i) != respostaDaTarefa) {
                palavrasComTamanhoCorreto.add(palavrasFiltradasPorConteudo.get(i));
            }
        }
        palavrasComTamanhoCorreto.add(respostaDaTarefa);
        Collections.shuffle(palavrasComTamanhoCorreto);
        return palavrasComTamanhoCorreto;
    }
}