package br.edu.ifal.enology.model;

import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private int pontuacaoDoAluno;
    private int codigoVerificacao;
    private boolean ativouConta;
    private int faseAtual = 1;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagem imagem;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", 
    joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "nome"))
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getPontuacaoDoAluno() {
        return pontuacaoDoAluno;
    }

    public void setPontuacaoDoAluno(int pontuacaoDoAluno) {
        this.pontuacaoDoAluno = pontuacaoDoAluno;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getCodigoVerificacao() {
        return codigoVerificacao;
    }

    public void setCodigoVerificacao(int codigoVerificacao) {
        this.codigoVerificacao = codigoVerificacao;
    }

    public boolean isAtivouConta() {
        return ativouConta;
    }

    public void setAtivouConta(boolean ativouConta) {
        this.ativouConta = ativouConta;
    }
    
    public int getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(int faseAtual) {
        this.faseAtual = faseAtual;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) this.roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return ativouConta;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}