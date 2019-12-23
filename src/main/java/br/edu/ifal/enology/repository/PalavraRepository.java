package br.edu.ifal.enology.repository;

import org.springframework.data.repository.CrudRepository;

import br.edu.ifal.enology.model.Palavra;

public interface PalavraRepository extends CrudRepository<Palavra,Long>{
    
}


