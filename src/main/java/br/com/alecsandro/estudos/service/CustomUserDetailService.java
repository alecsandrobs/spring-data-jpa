package br.com.alecsandro.estudos.service;

import br.com.alecsandro.estudos.model.Usuario;
import br.com.alecsandro.estudos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    UsuarioRepository dao;

    @Autowired
    public CustomUserDetailService(UsuarioRepository dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String usuarioNome) throws UsernameNotFoundException {
        Usuario usuario = Optional.ofNullable(dao.findByUsuario(usuarioNome))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
        List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
        return new User(usuario.getUsuario(), usuario.getSenha(), usuario.getAdmin() ? authorityListAdmin : authorityListUser);
    }
}
