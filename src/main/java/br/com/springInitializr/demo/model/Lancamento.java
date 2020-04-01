package br.com.springInitializr.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Lancamento {

    private Long id;
    private LocalDateTime data;
    private Fornecedor fornecedor;
    private Double valor;
    private LocalDateTime pagamento;
    public static List<Lancamento> lancamentosList;

    static {
        lancamentosRepository();
    }

    public Lancamento() {
    }

    public Lancamento(LocalDateTime data) {
        this.data = data;
    }

    public Lancamento(Long id, LocalDateTime data, Fornecedor fornecedor, Double valor, LocalDateTime pagamento) {
        this(data);
        this.id = id;
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.pagamento = pagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getPagamento() {
        return pagamento;
    }

    public void setPagamento(LocalDateTime pagamento) {
        this.pagamento = pagamento;
    }

    private static void lancamentosRepository() {
        lancamentosList = new ArrayList<>(asList(new Lancamento(1L, LocalDateTime.now(), new Fornecedor(1L, "Valdeci"), 1800D, LocalDateTime.of(2020, 3, 30, 10, 00)),
                new Lancamento(2L, LocalDateTime.now(), new Fornecedor(2L, "Agnaldo"), 3000.00, null)));
    }

}
