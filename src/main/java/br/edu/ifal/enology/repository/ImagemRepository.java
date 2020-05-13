package br.edu.ifal.enology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifal.enology.model.Imagem;

@Repository
@Transactional
public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    Imagem findByLink(Long link);
}