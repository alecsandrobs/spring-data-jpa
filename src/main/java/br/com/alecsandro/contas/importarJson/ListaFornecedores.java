package br.com.alecsandro.contas.importarJson;

import br.com.alecsandro.contas.javaClient.FornecedoresDAO;
import br.com.alecsandro.contas.model.Fornecedor;
import br.com.alecsandro.contas.util.Util;

import java.util.List;

public class ListaFornecedores {

    public static void main(String[] args) {
        FornecedoresDAO dao = new FornecedoresDAO();

        List<Fornecedor> fornecedores = dao.listAll();

        System.out.println();
        System.out.println("| Fornecedor                                |");
        fornecedores.forEach(fornecedor -> {
            String texto = String.format("| %s |", Util.completaEsquerda(fornecedor.getNome(), 40));
            System.out.println(texto);
        });
        System.out.println();
    }
}
