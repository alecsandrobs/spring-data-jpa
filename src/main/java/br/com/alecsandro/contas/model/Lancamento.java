package br.com.alecsandro.contas.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Table(name = "lancamentos")
public class Lancamento extends AbstractEntity {

    //    @NotEmpty(message = "A data de emissão é obrigatória")
    private LocalDate data;

    @ManyToOne
//    @NotEmpty(message = "O fornecedor é obrigatório")
    private Fornecedor fornecedor;

    //    @NotEmpty(message = "O valor é obrigatório")
    private Double valor;
    private LocalDate pagamento;
    private String observacoes;

    public Lancamento() {
    }

    public Lancamento(@NotEmpty(message = "A data de emissão é obrigatória") LocalDate data,
                      @NotEmpty(message = "O fornecedor é obrigatório") Fornecedor fornecedor,
                      @NotEmpty(message = "O valor é obrigatório") Double valor,
                      String observacoes) {
        this.data = data;
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.observacoes = observacoes;
    }

    public Lancamento(@NotEmpty(message = "A data de emissão é obrigatória") LocalDate data,
                      @NotEmpty(message = "O fornecedor é obrigatório") Fornecedor fornecedor,
                      @NotEmpty(message = "O valor é obrigatório") Double valor,
                      LocalDate pagamento,
                      String observacoes) {
        this.data = data;
        this.fornecedor = fornecedor;
        this.valor = valor;
        this.pagamento = pagamento;
        this.observacoes = observacoes;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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

    public LocalDate getPagamento() {
        return pagamento;
    }

    public void setPagamento(LocalDate pagamento) {
        this.pagamento = pagamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
