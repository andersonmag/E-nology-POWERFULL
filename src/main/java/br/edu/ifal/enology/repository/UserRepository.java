package br.edu.ifal.enology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifal.enology.model.Usuario;

@Transactional
public interface UserRepository extends JpaRepository<Usuario, Long>{
    Usuario findByEmail(String email);
}