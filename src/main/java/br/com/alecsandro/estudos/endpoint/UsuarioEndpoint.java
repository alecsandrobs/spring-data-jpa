package br.com.alecsandro.estudos.endpoint;

import br.com.alecsandro.estudos.error.ResourseNotFoundException;
import br.com.alecsandro.estudos.model.Usuario;
import br.com.alecsandro.estudos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("usuarios")
public class UsuarioEndpoint {

    private final UsuarioRepository dao;

    @Autowired
    public UsuarioEndpoint(UsuarioRepository dao) {
        this.dao = dao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Detalhes de usuário: " + userDetails);
        verifyIfExists(id);
        Optional<Usuario> usuario = dao.findById(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping(path = "/findByNome/{nome}")
    public ResponseEntity<?> findByNome(@PathVariable("nome") String nome) {
        return new ResponseEntity<>(dao.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Senha: " + passwordEncoder.encode(usuario.getSenha()));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return new ResponseEntity<>(dao.save(usuario), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Usuario usuario) {
        verifyIfExists(usuario.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println("Senha: " + passwordEncoder.encode(usuario.getSenha()));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return new ResponseEntity<>(dao.save(usuario), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyIfExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourseNotFoundException(String.format("Usuário não encontrado com o id: %s", id));
    }
}
