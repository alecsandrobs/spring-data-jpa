package br.com.alecsandro.estudos.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "fornecedores")
public class Fornecedor extends AbstractEntity {

    @NotEmpty(message = "O nome é obrigatório")
    private String name;

    public String getName() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
    }
}
