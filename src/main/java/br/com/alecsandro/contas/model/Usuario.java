package br.com.alecsandro.contas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "usuarios")
public class Usuario extends AbstractEntity {

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    @NotEmpty(message = "O usuário é obrigatório")
    @Column(unique = true)
    private String usuario;

    //    @JsonIgnore
    @NotEmpty(message = "A senha é obrigatória")
    private String senha;

    //    @NotEmpty(message = "É necessário informar se é administrador ou não")
    private Boolean admin;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
