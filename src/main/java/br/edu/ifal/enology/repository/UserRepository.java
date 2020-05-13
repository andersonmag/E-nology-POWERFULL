package br.edu.ifal.enology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifal.enology.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>{
    Usuario findByEmail(String email);
}