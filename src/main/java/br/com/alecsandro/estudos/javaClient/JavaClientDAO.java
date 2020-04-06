package br.com.alecsandro.estudos.javaClient;

import br.com.alecsandro.estudos.handler.RestResponseExceptionHandler;
import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.model.PageableResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

public class JavaClientDAO {

    private RestTemplate daoAdmin = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/fornecedores")
            .basicAuthentication("admin", "nimda")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    private RestTemplate dao = new RestTemplateBuilder()
            .rootUri("http://localhost:8080/fornecedores")
            .basicAuthentication("teste", "1234567")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    public Fornecedor findById(Long id) {
        return dao.getForObject("/{id}", Fornecedor.class, id);
    }

    public ResponseEntity<Fornecedor> getById(Long id) {
        return dao.getForEntity("/{id}", Fornecedor.class, id);
    }

    public List<Fornecedor> listAll() {
        ResponseEntity<PageableResponse<Fornecedor>> exchange = dao.exchange("/", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Fornecedor>>() {
                });
        return exchange.getBody().getContent();
    }

    public Fornecedor save(Fornecedor fornecedor) {
        ResponseEntity<Fornecedor> responseEntity = dao.exchange("/", HttpMethod.POST, new HttpEntity<>(fornecedor, createJsonHeader()), Fornecedor.class);
        return responseEntity.getBody();
    }

    public void update(Fornecedor fornecedor) {
        dao.put("/", fornecedor);
    }

    public void remove(Long id) {
        daoAdmin.delete("/{id}", id);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
