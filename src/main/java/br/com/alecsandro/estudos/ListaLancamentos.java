package br.com.alecsandro.estudos;

import br.com.alecsandro.estudos.javaClient.LancamentosDAO;
import br.com.alecsandro.estudos.model.Lancamento;
import br.com.alecsandro.estudos.util.DateUtil;
import br.com.alecsandro.estudos.util.Util;

import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
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
        System.out.println("| Data       | Fornecedor                                | Valor           | Observações                                                                                           |");
        lancamentos.forEach(lancamento -> {
            String texto = String.format("| %s | %s | %s | %s |", DateUtil.dateFormat(lancamento.getData()), completaEsquerda(lancamento.getFornecedor().getNome(), 40), completaDireita(Util.emReal(lancamento.getValor())), completaEsquerda(lancamento.getObservacoes(), 100));
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

    public static String completaDireita(String texto) {
        int tamanho = 15;
        int resto = 15 - texto.length();
        String completar = null;
        for (int i = 0; i < resto; i++) {
            if (i != 0) {
                completar += " ";
            } else {
                completar = " ";
            }
        }
        return completar + texto;
    }

    public static String completaEsquerda(String texto, int tamanho) {
        int tamanhoTexto = texto.length();
        if (tamanhoTexto > tamanho) {
            return texto.substring(0, tamanho) + " ";
        }
        while (texto.length() <= tamanho) {
            texto += " ";
        }
        return texto;
    }
}
