package br.edu.ifal.enology.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.model.Solucao;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.model.Usuario;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.SolucaoRepository;
import br.edu.ifal.enology.repository.TarefaRepository;

@Repository
public class SequenciadorService {

    @Autowired
    PalavraRepository palavraRepository;
    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    SolucaoRepository solucaoRepository;

    public Tarefa buscarTarefa(Usuario usuario) {
        List<Tarefa> todasTarefas = tarefaRepository.findAll();
        List<Solucao> solucoesDoAluno = solucaoRepository.findByAluno(usuario);
        List<Tarefa> tarefasRespondidasCorretamente = filtrarTarefasRespondidas(solucoesDoAluno);

        return selecionarTarefa(todasTarefas, tarefasRespondidasCorretamente);
    }

    private List<Tarefa> filtrarTarefasRespondidas(List<Solucao> solucoesDoAluno) {
        List<Tarefa> tarefasRespondidasCorretamente = new ArrayList<>();

        for (Solucao solucao : solucoesDoAluno) {
            if (solucao.isAcertou())
                tarefasRespondidasCorretamente.add(solucao.getTarefa());
        }

        return tarefasRespondidasCorretamente;
    }

    private Tarefa selecionarTarefa(List<Tarefa> todasTarefas, List<Tarefa> tarefasRespondidasCorretamente) {
        List<Tarefa> tarefasRestantes = new ArrayList<>();
        Random numeroAleatorio = new Random();
        int indexTarefa;

        for (Tarefa tarefa : todasTarefas) {
            if (tarefasRespondidasCorretamente.size() <= todasTarefas.size()
                    && tarefasRespondidasCorretamente.size() > 0) {
                if (!tarefasRespondidasCorretamente.contains(tarefa))
                    tarefasRestantes.add(tarefa);
            } else {
                break;
            }
        }

        if (tarefasRespondidasCorretamente.size() == todasTarefas.size())
            return null;
        else if (tarefasRestantes.isEmpty())
            tarefasRestantes = todasTarefas;

        indexTarefa = numeroAleatorio.nextInt(tarefasRestantes.size());

        return tarefasRestantes.get(indexTarefa);
    }

    public List<Palavra> buscarPalavrasPorConteudo(String conteudoTitulo, String respostaDaTarefa) {
        List<Palavra> palavrasEncontradas = new ArrayList<>();
        List<Palavra> palavras = palavraRepository.findAll();

        for (int i = 0; i < palavras.size(); i++) {
            for (int j = 0; j < palavras.get(i).getConteudos().size(); j++) {
                if (palavras.get(i).getConteudos().get(j) != null) {
                    System.out.println(palavras.get(i).getConteudos().get(j).getTitulo());
                    if (palavras.get(i).getConteudos().get(j).getTitulo().equals(conteudoTitulo)
                            && !palavras.get(i).getIngles().equals(respostaDaTarefa)) {
                        palavrasEncontradas.add(palavras.get(i));
                    }
                    continue;
                } else {
                    break;
                }
            }
        }
        return palavrasEncontradas;
    }
}