package br.com.springInitializr.demo.endpoint;

import br.com.springInitializr.demo.error.CustomErrorType;
import br.com.springInitializr.demo.model.Fornecedor;
import br.com.springInitializr.demo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fornecedores")
public class FornecedorEndpoint {

    private DateUtil dateUtil;

    @Autowired
    public FornecedorEndpoint(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @GetMapping
//    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(Fornecedor.fornecedoresList, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
//    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(id);
        int index = Fornecedor.fornecedoresList.indexOf(fornecedor);
        if (index == -1)
            return new ResponseEntity<>(new CustomErrorType("Fornecedor não encontrado."), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(Fornecedor.fornecedoresList.get(index), HttpStatus.OK);
    }

    @PostMapping
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Fornecedor fornecedor) {
        Fornecedor.fornecedoresList.add(fornecedor);
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }

    @PutMapping
//    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Fornecedor fornecedor) {
        Fornecedor.fornecedoresList.remove(fornecedor);
        Fornecedor.fornecedoresList.add(fornecedor);
        return new ResponseEntity<>(fornecedor, HttpStatus.OK);
    }

    @DeleteMapping
//    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestBody Fornecedor fornecedor) {
        Fornecedor.fornecedoresList.remove(fornecedor);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
