package br.edu.ifal.enology.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByConteudo(Conteudo conteudo);
}