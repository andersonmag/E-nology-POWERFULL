package br.edu.ifal.enology.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Palavra{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String portugues;
    private String ingles;
    // private byte[] imagem;
    private String definicao;
    private boolean termoTecnico;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Conteudo> conteudos;

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

    public List<Conteudo> getConteudos() {
        return conteudos;
    }

    public void setConteudos(List<Conteudo> conteudos) {
        this.conteudos = conteudos;
    }
}