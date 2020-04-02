package br.com.alecsandro.estudos.repository;

import br.com.alecsandro.estudos.model.Fornecedor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FornecedorRepository extends PagingAndSortingRepository<Fornecedor, Long> {

    List<Fornecedor> findByNameIgnoreCaseContaining(String name);
}
