package br.edu.ifal.enology.service;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.edu.ifal.enology.model.Imagem;
import br.edu.ifal.enology.repository.ImagemRepository;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public Imagem controiImagem(MultipartFile file)  {
        Long secretPassword = new Random().nextLong() + file.getOriginalFilename().length();
        Imagem imagem = new Imagem();

        try {
            imagem.setDados(file.getBytes());
            imagem.setLink(secretPassword);
            imagem.setNome(file.getOriginalFilename());
            imagem.setTipo(file.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imagem;
    }

    public Imagem salvar(MultipartFile file) {
        Imagem imagem = controiImagem(file);
        return imagemRepository.save(imagem);
    }

}
