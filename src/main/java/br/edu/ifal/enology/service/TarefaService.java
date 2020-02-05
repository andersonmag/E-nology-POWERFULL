package br.edu.ifal.enology.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Tarefa;
import br.edu.ifal.enology.repository.TarefaRepository;

@Repository
public class TarefaService {

    @Autowired
    TarefaRepository tarefaRepository;

    public Tarefa pegarTarefaPorId(Long id) {
        System.err.println(id);
        Optional<Tarefa> opTarefa = tarefaRepository.findById(id);

        if (opTarefa.isPresent())
            return opTarefa.get();
        return null;
    }
}