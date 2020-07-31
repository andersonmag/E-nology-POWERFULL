package br.edu.ifal.enology.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.RedefinicaoSenha;

@Repository
public interface RedefinicaoSenhaRepository extends JpaRepository<RedefinicaoSenha, Long>{
    Optional<RedefinicaoSenha> findByToken(String token);
}