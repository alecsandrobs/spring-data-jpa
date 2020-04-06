package br.com.alecsandro.estudos.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "lancamentos")
public class Lancamento extends AbstractEntity {

    @NotEmpty(message = "A data de emissão é obrigatória")
    private LocalDateTime data;

    @ManyToOne
    @NotEmpty(message = "O fornecedor é obrigatório")
    private Fornecedor fornecedor;

    @NotEmpty(message = "O valor é obrigatório")
    private Double valor;
    private LocalDateTime pagamento;

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
}
