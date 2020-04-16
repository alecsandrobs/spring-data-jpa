package br.com.alecsandro.contas.repository;

import br.com.alecsandro.contas.model.Fornecedor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FornecedorRepository extends PagingAndSortingRepository<Fornecedor, Long> {

    List<Fornecedor> findByNomeIgnoreCaseContaining(String nome);
}
