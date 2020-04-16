package br.com.alecsandro.contas.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "fornecedores")
public class Fornecedor extends AbstractEntity {

    @NotEmpty(message = "O nome é obrigatório")
    private String nome;

    public Fornecedor() {
    }

    public Fornecedor(@NotEmpty(message = "O nome é obrigatório") String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "nome='" + nome + '\'' +
                '}';
    }
}
