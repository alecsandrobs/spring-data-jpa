package br.com.alecsandro.estudos.repository;

import br.com.alecsandro.estudos.model.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
    List<Usuario> findByNomeIgnoreCaseContaining(String nome);

    Usuario findByUsuario(String usuario);
}
