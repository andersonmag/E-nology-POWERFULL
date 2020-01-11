package br.edu.ifal.enology.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.repository.PalavraRepository;
import br.edu.ifal.enology.repository.TarefaRepository;

@Repository
public class SequenciadorService {

    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    PalavraRepository palavraRepository;

    // public List<Tarefa> buscarTarefa(Conteudo conteudo){

    // // List<Tarefa> tarefas = tareafRepository.findAll();
    // List<Palavra> palavras = conteudo.getPalavras();
    // List<Tarefa> TarefasFiltradas = tarefaRepository.findByResposta(palavras);

    // return TarefasFiltradas;
    // }

    public List<Palavra> buscarPalavrasPorConteudo(String conteudoTitulo, String respostaDaTarefa) {
        System.out.println(conteudoTitulo);
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
                }

                else {

                    break;
                }
            }
        }

        return palavrasEncontradas;
    }
}