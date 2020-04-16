package br.com.alecsandro.estudos.javaClient;

import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.model.Lancamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LancamentosSpringClientTest {

    public static void main(String[] args) {

        FornecedoresDAO fornecedoresDAO = new FornecedoresDAO();

        String nome = "Valdeci";
        List<Fornecedor> fornecedores = fornecedoresDAO.findByNome(nome);

        Fornecedor fornecedorSelecionado = null;
        for (Fornecedor fornecedor : fornecedores) {
            if (nome.equalsIgnoreCase(fornecedor.getNome())) {
                fornecedorSelecionado = fornecedor;
            }
        }

        if (fornecedorSelecionado == null) {
            System.out.println(String.format("[Não encontrou o fornecedor %s]", nome));
            throw new RuntimeException(String.format("[Não encontrou o fornecedor %s]", nome));
        }

        Lancamento lancamento = new Lancamento(LocalDate.now(), fornecedorSelecionado, 123.45, null, "Primeira inserção de lançamento.");

        LancamentosDAO dao = new LancamentosDAO();

//        POST
        dao.save(lancamento);

//        PUT
//        lancamento.setId(38L);
//        lancamento.setNome("Atualização com Java Puro");
//        dao.update(lancamento);

//        GET BY ID
//        System.out.println(dao.findById(38L));

//        GET BY NAME
//        dao.

//        DELETE
//        dao.remove(39L);

//        GET ALL
//        System.out.println("Listando os lançamentos");
//        System.out.println();
//        dao.listAll().forEach(item -> {
//            System.out.println(String.format("%s. lançamento", item.getId()));
//            System.out.println(item.getData());
//            System.out.println(item.getFornecedor().getNome());
//            System.out.println(item.getValor());
//            System.out.println(item.getPagamento());
//            System.out.println(item.getObservacoes());
//            System.out.println();
//        });
    }
}
