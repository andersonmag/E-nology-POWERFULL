package br.edu.ifal.enology.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Solucao;
import br.edu.ifal.enology.model.Usuario;

public interface SolucaoRepository extends CrudRepository<Solucao, Long>{

    List<Solucao>findByAluno(Usuario aluno);
}
