package br.com.alecsandro.estudos.repository;

import br.com.alecsandro.estudos.model.Fornecedor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FornecedorRepository extends CrudRepository<Fornecedor, Long> {

    List<Fornecedor> findByNameIgnoreCaseContaining(String name);
}
