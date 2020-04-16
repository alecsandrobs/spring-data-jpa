package br.com.alecsandro.contas.javaClient;

import br.com.alecsandro.contas.model.Fornecedor;

public class FornecedoresSpringClientTest {

    public static void main(String[] args) {

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Criação com Java Puro");

        FornecedoresDAO dao = new FornecedoresDAO();

//        POST
//        dao.save(fornecedor);

//        PUT
//        fornecedor.setId(38L);
//        fornecedor.setNome("Atualização com Java Puro");
//        dao.update(fornecedor);

//        GET BY ID
//        System.out.println(dao.findById(38L));

//        GET BY NAME
//        dao.

//        DELETE
//        dao.remove(39L);

//        GET ALL
        System.out.println("Listando os fornecedores");
        System.out.println();
        dao.listAll().forEach(item -> {
            System.out.println(item.getNome());
        });
    }
}
