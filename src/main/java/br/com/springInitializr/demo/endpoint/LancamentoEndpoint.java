package br.com.springInitializr.demo.endpoint;

import br.com.springInitializr.demo.error.CustomErrorType;
import br.com.springInitializr.demo.model.Lancamento;
import br.com.springInitializr.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("lancamentos")
public class LancamentoEndpoint {


    private DateUtil dateUtil;

    @Autowired
    public LancamentoEndpoint(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(Lancamento.lancamentosList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(id);
        int index = Lancamento.lancamentosList.indexOf(lancamento);
        if (index == -1)
            return new ResponseEntity<>(new CustomErrorType("Lançamento não encontrado."), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Lancamento.lancamentosList.get(index), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Lancamento lancamento) {
        Lancamento.lancamentosList.add(lancamento);
        return new ResponseEntity<>(lancamento, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Lancamento lancamento) {
        Lancamento.lancamentosList.remove(lancamento);
        Lancamento.lancamentosList.add(lancamento);
        return new ResponseEntity<>(lancamento, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Lancamento lancamento) {
        Lancamento.lancamentosList.remove(lancamento);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
