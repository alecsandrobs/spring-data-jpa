package br.com.alecsandro.contas.importarJson;

import br.com.alecsandro.contas.javaClient.LancamentosDAO;
import br.com.alecsandro.contas.model.Lancamento;
import br.com.alecsandro.contas.util.DateUtil;
import br.com.alecsandro.contas.util.Util;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListaLancamentos {

    public static void main(String[] args) {
        LancamentosDAO dao = new LancamentosDAO();

        List<Lancamento> lancamentos = dao.listAll();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("| Data       | Fornecedor                                | Valor           | Observações                                                                                           | Pago em    |");
        lancamentos.forEach(lancamento -> {
            String texto = String.format("| %s | %s | %s | %s | %s |", DateUtil.dateFormat(lancamento.getData()), Util.completaEsquerda(lancamento.getFornecedor().getNome(), 40), Util.completaDireita(Util.emReal(lancamento.getValor())), Util.completaEsquerda(lancamento.getObservacoes(), 100), lancamento.getPagamento() != null ? lancamento.getPagamento() : "--/--/----");
            System.out.println(texto);
        });

        System.out.println();
        System.out.println();
        System.out.println();

        Predicate<Lancamento> pagamento = lancamento -> lancamento.getPagamento() != null;
        Function<Lancamento, Double> valores = lancamento -> lancamento.getValor();
        BinaryOperator<Double> soma = (acumulador, valor) -> acumulador + valor;

        Double pago = lancamentos.parallelStream()
                .filter(pagamento)
                .map(valores)
                .reduce(soma).get();

        Double total = lancamentos.parallelStream()
                .map(valores)
                .reduce(soma).get();

        System.out.println(String.format("Pago: %s", Util.emReal(pago)));
        System.out.println(String.format("Pagar: %s", Util.emReal(total - pago)));
        System.out.println(String.format("Total: %s", Util.emReal(total)));
    }
}
