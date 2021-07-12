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
        return tarefasRespondidasCorretamente.stream().anyMatch(tarefa -> tarefa.getConteudo().equals(conteudo));
    }

    public boolean isProximoConteudo(Conteudo conteudo, List<Tarefa> tarefasRespondidasCorretamente,
            List<Tarefa> tarefasConteudo, Integer faseAtual) {

        if (tarefasRespondidasCorretamente.isEmpty() && conteudo.getId() == 1L) {
            return true;
        }

        else if (verificarSolucaoExistenteComConteudo(conteudo, tarefasRespondidasCorretamente))
            return true;

        Long proximoConteudo = conteudo.getId();
        boolean isProximo = faseAtual.equals(proximoConteudo.intValue());

        return isProximo;
    }

    public Tarefa buscarTarefa(Usuario usuario, Conteudo conteudo) {
        List<Solucao> solucoesDoAluno = solucaoService.buscarPorUsuario(usuario);
        List<Tarefa> tarefasRespondidasCorretamente = filtrarTarefasRespondidas(solucoesDoAluno);
        List<Tarefa> tarefasConteudo = tarefaService.buscarPorConteudo(conteudo).subList(0, 3);

        if (solucoesDoAluno.isEmpty()) {
            if (!conteudo.getId().equals(1L)) {
                conteudo = conteudoService.findById(1L);
                tarefasConteudo = tarefaService.buscarPorConteudo(conteudo).subList(0, 3);
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

    public List<Tarefa> filtrarTarefasRespondidasPorConteudo(List<Solucao> solucoesDoAlunoDoConteudo,
            Conteudo conteudo) {
        List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

        for (Solucao solucao : solucoesDoAlunoDoConteudo) {
            if (solucao.isAcertou() && solucao.getTarefa().getConteudo().equals(conteudo))
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

    public List<Palavra> buscarPalavrasPorConteudoTexto(int idTarefa) {
        List<Palavra> palavrasFiltradasPorConteudo = new ArrayList<>();
        Conteudo conteudo = new Conteudo();

        switch (idTarefa) {
            case 1:
                conteudo.setId(6L);
                palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
                break;

            case 7:
                conteudo.setId(7L);
                palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
                break;

            case 8:
                conteudo.setId(8L);
                palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
                break;

            case 9:
                conteudo.setId(9L);
                palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
                break;

            case 10:
                conteudo.setId(10L);
                palavrasFiltradasPorConteudo = palavraRepository.findByConteudos(conteudo);
                break;
        }

        Collections.shuffle(palavrasFiltradasPorConteudo);
        return palavrasFiltradasPorConteudo;
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