package br.edu.ifal.enology.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, Long> {
    
    List<Tarefa> findAll();
}