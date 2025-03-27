package br.edu.ifal.enology.service;

import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.repository.ConteudoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConteudoService {

	final ConteudoRepository conteudoRepository;

	public ConteudoService(ConteudoRepository conteudoRepository) {
		this.conteudoRepository = conteudoRepository;
	}

	public List<Conteudo> findAll() {
		return conteudoRepository.findAll();
	}

	public Conteudo findById(Long id) {
		return conteudoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conteudo não existente!"));
	}
}