package br.edu.ifal.enology.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Tarefa{
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pontuacao;
    private Nivel nivel;
    public TipoTarefa tipoTarefa;
    private String enunciado;

    @OneToOne
    private Texto texto;

    @ManyToOne
    private Conteudo conteudo;

    @ManyToOne
    private Palavra resposta;

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

    public TipoTarefa getTipoTarefa(){
        return tipoTarefa;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }

    public void setTexto(Texto texto) {
        this.texto = texto;
    }

    public Texto getTexto() {
        return texto;
    }
}