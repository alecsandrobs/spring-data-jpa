package br.com.alecsandro.estudos.repository;

import br.com.alecsandro.estudos.model.Lancamento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LancamentoRepository extends CrudRepository<Lancamento, Long> {

    List<Lancamento> findByFornecedorNameIgnoreCaseContaining(String name);
}
