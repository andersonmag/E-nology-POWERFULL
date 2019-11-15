package br.edu.ifal.enology.repository;

import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Usuario;

public interface RepositoryUser extends CrudRepository<Usuario, Long>{

    Usuario findBySenha(String senha);

}