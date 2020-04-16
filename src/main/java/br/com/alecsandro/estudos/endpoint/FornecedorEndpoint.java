package br.com.alecsandro.estudos.endpoint;

import br.com.alecsandro.estudos.error.ResourseNotFoundException;
import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("fornecedores")
public class FornecedorEndpoint {

    private final FornecedorRepository dao;

    @Autowired
    public FornecedorEndpoint(FornecedorRepository dao) {
        this.dao = dao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        verifyIfExists(id);
        Optional<Fornecedor> fornecedor = dao.findById(id);
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }

    @GetMapping(path = "/findByNome/{nome}")
    public ResponseEntity<?> findByNome(@PathVariable String nome) {
        return new ResponseEntity<>(dao.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@Valid @RequestBody Fornecedor fornecedor) {
        return new ResponseEntity<>(dao.save(fornecedor), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Fornecedor fornecedor) {
        verifyIfExists(fornecedor.getId());
        return new ResponseEntity<>(dao.save(fornecedor), HttpStatus.OK);
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
            throw new ResourseNotFoundException(String.format("Fornecedor não encontrado com o id: %s", id));
    }
}
