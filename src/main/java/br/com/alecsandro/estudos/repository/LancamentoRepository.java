package br.com.alecsandro.estudos.repository;

import br.com.alecsandro.estudos.model.Lancamento;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LancamentoRepository extends PagingAndSortingRepository<Lancamento, Long> {

    List<Lancamento> findByFornecedorNomeIgnoreCaseContaining(String name);
}
