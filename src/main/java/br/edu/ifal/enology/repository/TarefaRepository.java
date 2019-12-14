package br.edu.ifal.enology.repository;

import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, Long>{

    Iterable<Tarefa> findByRespostaPortugues(String palavra);
}