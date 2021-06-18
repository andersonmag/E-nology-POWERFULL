package br.edu.ifal.enology.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Texto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(columnDefinition="LONGTEXT")
    private String conteudo;
    private String fonteConteudo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagem imagem;

    private String fonteImagem;

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getFonteConteudo() {
        return fonteConteudo;
    }

    public void setFonteConteudo(String fonteConteudo) {
        this.fonteConteudo = fonteConteudo;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public String getFonteImagem() {
        return fonteImagem;
    }

    public void setFonteImagem(String fonteImagem) {
        this.fonteImagem = fonteImagem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
