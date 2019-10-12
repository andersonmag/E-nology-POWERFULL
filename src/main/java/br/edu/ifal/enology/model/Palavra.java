package br.edu.ifal.enology.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Palavra{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String portugues;
    private String ingles;
    private byte[] imagem;
    private String definição;
    private boolean termoTecnico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortugues() {
        return portugues;
    }

    public void setPortugues(String portugues) {
        this.portugues = portugues;
    }

    public String getIngles() {
        return ingles;
    }

    public void setIngles(String ingles) {
        this.ingles = ingles;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getDefinição() {
        return definição;
    }

    public void setDefinição(String definição) {
        this.definição = definição;
    }

    public boolean isTermoTecnico() {
        return termoTecnico;
    }

    public void setTermoTecnico(boolean termoTecnico) {
        this.termoTecnico = termoTecnico;
    }

}