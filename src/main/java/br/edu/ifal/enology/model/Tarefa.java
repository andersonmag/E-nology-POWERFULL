package br.edu.ifal.enology.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tarefa{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean status;
    private int pontuacao;
    private String resposta;
    private Nivel nivel;
    private TipoTarefa tipoTarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
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
}