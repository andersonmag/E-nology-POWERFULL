package br.edu.ifal.enology.repository;

import org.springframework.data.repository.CrudRepository;
import br.edu.ifal.enology.model.Usuario;

public interface UserRepository extends CrudRepository<Usuario, Long>{

    Usuario findByEmail(String email);
}