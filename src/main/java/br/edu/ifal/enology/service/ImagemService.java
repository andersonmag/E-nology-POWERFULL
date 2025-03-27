package br.edu.ifal.enology.service;

import br.edu.ifal.enology.model.Imagem;
import br.edu.ifal.enology.repository.ImagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagemService {

	@Autowired
	private ImagemRepository imagemRepository;

	public Imagem salvar(MultipartFile file) {
		Imagem imagem;
		try {
			imagem = controiImagem(file);
			return imagemRepository.save(imagem);
		}
		catch (IOException ex) {
			throw new RuntimeException("Erro ao salvar imagem", ex);
		}
	}

	public Imagem controiImagem(MultipartFile file) throws IOException {
		Imagem imagem = new Imagem();
		imagem.setDados(file.getBytes());
		imagem.setNome(file.getOriginalFilename());
		imagem.setTipo(file.getContentType());

		return imagem;
	}
}
