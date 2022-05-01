package br.edu.ifal.enology.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    
    Usuario findByEmail(String email);
    boolean existsByEmail(String email);
    List<Usuario> findByRolesIsNull();

    @Query(value = "SELECT AVG(u.pontuacao_do_aluno) FROM Usuario u " +
           " WHERE (SELECT usuario_id FROM usuarios_roles WHERE usuario_id = u.id) IS NULL", 
           nativeQuery = true) 
    Double calculateAverageScores();
}