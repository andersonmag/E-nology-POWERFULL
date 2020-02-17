package br.edu.ifal.enology.service;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        Optional<Tarefa> opTarefa = tarefaRepository.findById(id);

        if (opTarefa.isPresent())
            return opTarefa.get();
        return null;
    }

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }
}