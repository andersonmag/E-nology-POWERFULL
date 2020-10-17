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
    PalavraRepository palavraRepository;
    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    SolucaoRepository solucaoRepository;
    @Autowired
    ConteudoRepository conteudoRepository;

    public Tarefa buscarTarefa(Usuario usuario) {
        List<Tarefa> todasTarefas = tarefaRepository.findAll();
        List<Solucao> solucoesDoAluno = solucaoRepository.findByAluno(usuario);
        List<Tarefa> tarefasRespondidasCorretamente = filtrarTarefasRespondidas(solucoesDoAluno);

        return selecionarTarefa(todasTarefas, tarefasRespondidasCorretamente);
    }

    public List<Tarefa> filtrarTarefasRespondidas(List<Solucao> solucoesDoAluno) {
        List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

        for (Solucao solucao : solucoesDoAluno) {
            if (solucao.isAcertou())
                tarefasRespondidasCorretamente.add(solucao.getTarefa());
        }
        return tarefasRespondidasCorretamente;
    }

    private Tarefa selecionarTarefa(List<Tarefa> todasTarefas, List<Tarefa> tarefasRespondidasCorretamente) {
        List<Tarefa> tarefasRestantesTexto = new ArrayList<>();
        List<Tarefa> tarefasRestantesGeral = new ArrayList<>();

        Random numeroAleatorio = new Random();
        int indexTarefa;

        for (Tarefa tarefa : todasTarefas) {
            if (tarefasRespondidasCorretamente.size() <= todasTarefas.size()) {
                if (tarefasRespondidasCorretamente.isEmpty() || !tarefasRespondidasCorretamente.contains(tarefa)){
                    if(tarefa.geTipoTarefa() == TipoTarefa.MULTIPLA_ESCOLHA_TEXTO){
                        tarefasRestantesTexto.add(tarefa);
                    }else{
                        tarefasRestantesGeral.add(tarefa);
                    }
                }
            } else {
                break;
            }
        }

        if (tarefasRespondidasCorretamente.size() == todasTarefas.size())
            return null;

        if(tarefasRestantesTexto.size() > 0){
            indexTarefa = numeroAleatorio.nextInt(tarefasRestantesTexto.size());
            return tarefasRestantesTexto.get(indexTarefa);
        }
        else{
            indexTarefa = numeroAleatorio.nextInt(tarefasRestantesGeral.size());
            return tarefasRestantesGeral.get(indexTarefa);
        }
    }

    public Conteudo pegarConteudoAleatorio(Palavra respostaDaTarefa) {
        Random numeroAleatorio = new Random();
        List<Conteudo> todosConteudos = conteudoRepository.findAll();
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