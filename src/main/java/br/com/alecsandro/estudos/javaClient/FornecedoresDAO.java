package br.com.alecsandro.estudos.javaClient;

import br.com.alecsandro.estudos.handler.RestResponseExceptionHandler;
import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.model.PageableResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class FornecedoresDAO {

    private final String uri = "http://localhost:8080";
    private final String endPoint = "/fornecedores";
    private final String endPointId = String.format("%s/{id}", endPoint);

    private RestTemplate daoAdmin = new RestTemplateBuilder()
            .rootUri(uri)
            .basicAuthentication("admin", "nimda")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    private RestTemplate dao = new RestTemplateBuilder()
            .rootUri(uri)
            .basicAuthentication("teste", "1234567")
            .errorHandler(new RestResponseExceptionHandler())
            .build();

    public Fornecedor findById(Long id) {
        return dao.getForObject(endPointId, Fornecedor.class, id);
    }

    public ResponseEntity<Fornecedor> getById(Long id) {
        return dao.getForEntity(endPointId, Fornecedor.class, id);
    }

    public List<Fornecedor> listAll() {
        ResponseEntity<PageableResponse<Fornecedor>> exchange = dao.exchange(endPoint, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Fornecedor>>() {
                });
        return exchange.getBody().getContent();
    }

    public List<Fornecedor> findByNome(String nome) {
        ResponseEntity<List<Fornecedor>> exchange = dao.exchange("/fornecedores/findByNome/{nome}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Fornecedor>>() {
                }, nome);
        return exchange.getBody();
    }

    public Fornecedor save(Fornecedor fornecedor) {
        ResponseEntity<Fornecedor> responseEntity = dao.exchange(endPoint, HttpMethod.POST, new HttpEntity<>(fornecedor, createJsonHeader()), Fornecedor.class);
        return responseEntity.getBody();
    }

    public void update(Fornecedor fornecedor) {
        dao.put(endPoint, fornecedor);
    }

    public void remove(Long id) {
        daoAdmin.delete(endPointId, id);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
