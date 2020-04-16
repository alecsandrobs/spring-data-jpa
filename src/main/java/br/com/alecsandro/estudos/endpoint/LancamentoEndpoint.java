package br.com.alecsandro.estudos.endpoint;

import br.com.alecsandro.estudos.error.ResourseNotFoundException;
import br.com.alecsandro.estudos.model.Lancamento;
import br.com.alecsandro.estudos.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("lancamentos")
public class LancamentoEndpoint {

    private final LancamentoRepository dao;

    @Autowired
    public LancamentoEndpoint(LancamentoRepository dao) {
        this.dao = dao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        verifyIfExists(id);
        Optional<Lancamento> lancamento = dao.findById(id);
        return new ResponseEntity<>(lancamento, HttpStatus.OK);
    }

    @GetMapping(path = "/findByFornecedorName/{name}")
    public ResponseEntity<?> findByFornecedorName(@PathVariable String nome) {
        return new ResponseEntity<>(dao.findByFornecedorNomeIgnoreCaseContaining(nome), HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(@RequestBody Lancamento lancamento) {
        return new ResponseEntity<>(dao.save(lancamento), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Lancamento lancamento) {
        verifyIfExists(lancamento.getId());
        return new ResponseEntity<>(dao.save(lancamento), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyIfExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourseNotFoundException(String.format("Lançamento não encontrado com o id: %s", id));
    }
}
