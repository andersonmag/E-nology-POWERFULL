package br.edu.ifal.enology.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Palavra{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String portugues;
    private String ingles;
    // private byte[] imagem;
    private String definicao;
    private boolean termoTecnico;
    
    @OneToOne
    private Topico topico;

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

    // public byte[] getImagem() {
    //     return imagem;
    // }

    // public void setImagem(byte[] imagem) {
    //     this.imagem = imagem;
    // }

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    public boolean getTermoTecnico() {
        return termoTecnico;
    }

    public void setTermoTecnico(boolean termoTecnico) {
        this.termoTecnico = termoTecnico;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }
}