package br.com.alecsandro.estudos.javaClient;

import br.com.alecsandro.estudos.handler.RestResponseExceptionHandler;
import br.com.alecsandro.estudos.model.Lancamento;
import br.com.alecsandro.estudos.model.PageableResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class LancamentosDAO {

    private final String uri = "http://localhost:8080";
    private final String endPoint = "/lancamentos";
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

    public Lancamento findById(Long id) {
        return dao.getForObject(endPointId, Lancamento.class, id);
    }

    public ResponseEntity<Lancamento> getById(Long id) {
        return dao.getForEntity(endPointId, Lancamento.class, id);
    }

    public List<Lancamento> listAll() {
        ResponseEntity<PageableResponse<Lancamento>> exchange = dao.exchange(endPoint, HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Lancamento>>() {
                });
        return exchange.getBody().getContent();
    }

    public Lancamento save(Lancamento lancamento) {
        ResponseEntity<Lancamento> responseEntity = dao.exchange(endPoint, HttpMethod.POST, new HttpEntity<>(lancamento, createJsonHeader()), Lancamento.class);
        return responseEntity.getBody();
    }

    public void update(Lancamento lancamento) {
        dao.put(endPoint, lancamento);
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
