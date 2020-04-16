package br.com.alecsandro.contas.repository;

import br.com.alecsandro.contas.model.Lancamento;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LancamentoRepository extends PagingAndSortingRepository<Lancamento, Long> {

    List<Lancamento> findByFornecedorNomeIgnoreCaseContaining(String nome);
}
