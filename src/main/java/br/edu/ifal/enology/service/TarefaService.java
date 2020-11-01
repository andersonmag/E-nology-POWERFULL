package br.edu.ifal.enology.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.repository.TarefaRepository;

@Service
public class TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    public void save(@Valid Tarefa tarefa) {
        tarefaRepository.save(tarefa);
    }

    public Tarefa findById(Long id) {
       return tarefaRepository.findById(id).orElse(null);  
    }

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> buscarPorConteudo(Conteudo conteudo) {
        return tarefaRepository.findByConteudo(conteudo);
    }
}