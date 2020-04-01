package br.com.springInitializr.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Fornecedor {

    private Long id;
    private String nome;
    public static List<Fornecedor> fornecedoresList;

    static {
        fornecedoresRepository();
    }

    public Fornecedor() {
    }

    public Fornecedor(String nome) {
        this.nome = nome;
    }

    public Fornecedor(Long id, String nome) {
        this(nome);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fornecedor that = (Fornecedor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private static void fornecedoresRepository() {
        fornecedoresList = new ArrayList<>(asList(new Fornecedor(2L, "Agnaldo"), new Fornecedor(3L, "Eder"), new Fornecedor(1L, "Valdeci")));
    }
}
