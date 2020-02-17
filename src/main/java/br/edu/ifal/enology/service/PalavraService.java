package br.edu.ifal.enology.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.ifal.enology.model.Palavra;
import br.edu.ifal.enology.repository.PalavraRepository;

@Service
public class PalavraService {

    @Autowired
    PalavraRepository palavraRepository;

    public void save(@Valid Palavra palavra) {
        palavraRepository.save(palavra);
    }

    public List<Palavra> findAll() {
        return palavraRepository.findAll();
    }
}