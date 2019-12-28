package br.edu.ifal.enology.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tarefa{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pontuacao;
    
    @ManyToOne
    private Palavra resposta;
    private Nivel nivel;
    private TipoTarefa tipoTarefa;
    private String enunciado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Palavra getResposta() {
        return resposta;
    }

    public void setResposta(Palavra resposta) {
        this.resposta = resposta;
    }
   
    public void setNivel(Nivel nivel){
        this.nivel = nivel;
    }

    public Nivel getNivel(){
        return nivel;
    }

    public void setTipoTarefa(TipoTarefa tipoTarefa){
        this.tipoTarefa = tipoTarefa;
    }

    public TipoTarefa geTipoTarefa(){
        return tipoTarefa;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
}