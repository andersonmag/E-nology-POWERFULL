package br.edu.ifal.enology.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Frase{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ingles;
    private String portugues;

    @ManyToMany
    private List<Palavra> palavras;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngles() {
        return ingles;
    }

    public void setIngles(String ingles) {
        this.ingles = ingles;
    }

    public String getPortugues() {
        return portugues;
    }

    public void setPortugues(String portugues) {
        this.portugues = portugues;
    }

    public List<Palavra> getPalavras() {
        return palavras;
    }

    public void setPalavras(List<Palavra> palavras) {
        this.palavras = palavras;
    }
    
}