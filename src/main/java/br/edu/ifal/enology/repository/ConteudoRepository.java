package br.edu.ifal.enology.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Conteudo;

public interface ConteudoRepository extends CrudRepository<Conteudo ,Long>{
    
    List<Conteudo> findAll();
}