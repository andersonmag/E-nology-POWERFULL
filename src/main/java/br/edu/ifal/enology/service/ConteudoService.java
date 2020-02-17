package br.edu.ifal.enology.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Conteudo;
import br.edu.ifal.enology.repository.ConteudoRepository;

@Service
public class ConteudoService {

    @Autowired
    ConteudoRepository conteudoRepository;

    public void save(@Valid Conteudo conteudo) {
        conteudoRepository.save(conteudo);
    }

    public List<Conteudo> findAll() {
        return conteudoRepository.findAll();
    }
}