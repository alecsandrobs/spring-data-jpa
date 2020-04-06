package br.com.alecsandro.estudos.javaClient;

import br.com.alecsandro.estudos.model.Fornecedor;

public class JavaSpringClientTest {

    public static void main(String[] args) {

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome("Criação com Java Puro");

        JavaClientDAO dao = new JavaClientDAO();

//        POST
//        dao.save(fornecedor);

//        PUT
//        fornecedor.setId(38L);
//        fornecedor.setNome("Atualização com Java Puro");
//        dao.update(fornecedor);

//        GET BY ID
//        System.out.println(dao.findById(38L));

//        DELETE
//        dao.remove(39L);

//        GET ALL
        dao.listAll().forEach(item -> {
            System.out.println(item.getName());
        });
    }
}
