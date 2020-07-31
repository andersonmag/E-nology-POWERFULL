package br.edu.ifal.enology.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "redefinicao_senha_usuarios")
public class RedefinicaoSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Usuario usuario;
    
    @Column(name = "token_usuario", unique = true,
            updatable = false, nullable = false)
    private String token;

    @Column(name = "tempo_limite", updatable = false, unique = false)
    private LocalDateTime timeout;

    public RedefinicaoSenha() {
        UUID uuid = UUID.randomUUID();
		this.token = uuid.toString().substring(0, 23).replaceAll("-", "");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getTimeout() {
        return timeout;
    }

    public void setTimeout(LocalDateTime timeout) {
        Long duracaoToken = 12L; //12 Horas
        this.timeout = timeout.plusHours(duracaoToken);
    }

}