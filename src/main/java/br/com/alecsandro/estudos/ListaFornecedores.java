package br.com.alecsandro.estudos;

import br.com.alecsandro.estudos.javaClient.FornecedoresDAO;
import br.com.alecsandro.estudos.model.Fornecedor;

import java.util.List;

public class ListaFornecedores {

    public static void main(String[] args) {
        FornecedoresDAO dao = new FornecedoresDAO();

        List<Fornecedor> fornecedores = dao.listAll();

        System.out.println();
        System.out.println("---=== Fornecedores ===---");
        fornecedores.forEach(fornecedor -> System.out.println(fornecedor.getNome()));
        System.out.println("---=== Fornecedores ===---");
        System.out.println();
    }
}
