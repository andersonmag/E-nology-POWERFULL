package br.edu.ifal.enology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.ifal.enology.model.Texto;

@Repository
public interface TextoTarefaRepository extends JpaRepository<Texto, Long> {
}