package br.edu.ifal.enology.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.ifal.enology.model.Imagem;

public interface ImagemRepository extends JpaRepository<Imagem, Long> {
    Imagem findByLink(Long link);
}