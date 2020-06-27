package br.com.alecsandro.contas;

import br.com.alecsandro.contas.model.Fornecedor;
import br.com.alecsandro.contas.repository.FornecedorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FornecedorEndpointTokenTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    private HttpEntity<Void> headerTeste;
    private HttpEntity<Void> headerAdmin;
    private HttpEntity<Void> headerInvalido;

    @Before
    public void configProtectedHeaders() {
        String texto = "{\"usuario\": \"teste\", \"senha\": \"1234567\"}";
        HttpHeaders httpHeaders = restTemplate.postForEntity("/login", texto, String.class).getHeaders();
        this.headerTeste = new HttpEntity<>(httpHeaders);
    }

    @Before
    public void configadminHeaders() {
        String texto = "{\"usuario\": \"admin\", \"senha\": \"nimda\"}";
        HttpHeaders httpHeaders = restTemplate.postForEntity("/login", texto, String.class).getHeaders();
        this.headerAdmin = new HttpEntity<>(httpHeaders);
    }

    @Before
    public void configWrongHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "1234567890987654321");
//        String texto = "{\"usuario\": \"Nome do Usuário\", \"senha\": \"N0vaS3na\"}";
//        HttpHeaders httpHeaders = restTemplate.postForEntity("/login", texto, String.class).getHeaders();
        this.headerInvalido = new HttpEntity<>(httpHeaders);
    }

    @Before
    public void setup() {
        Fornecedor fornecedor = new Fornecedor(1L, "Almir Armando Pontes");
        BDDMockito.when(fornecedorRepository.findById(1L)).thenReturn(java.util.Optional.of(fornecedor));
    }

    @Test
    public void listFornecedoresWhenUserTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores", GET, headerInvalido, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getFornecedoresByIdWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores/1", GET, headerInvalido, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listFornecedoresWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores", GET, headerTeste, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getFornecedoresByIdWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<Fornecedor> response = restTemplate.exchange("/fornecedores/{id}", GET, headerTeste, Fornecedor.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getFornecedoresByIdWhenTokenIsCorrectAndFornecedorDoesNotExistsShouldReturnStatusCode404() {
        ResponseEntity<Fornecedor> response = restTemplate.exchange("/fornecedores/{id}", GET, headerTeste, Fornecedor.class, 2L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndFornecedorExistsShouldReturnSatusCode204() {
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(1L, "Teste"));
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores/{id}", DELETE, headerAdmin, String.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndFornecedorDoesNotExistsShouldReturnSatusCode404() {
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores/{id}", DELETE, headerAdmin, String.class, 2L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndFornecedorDoesNotExistsShouldReturnSatusCode404WithMockMvc() throws Exception {
        String token = headerAdmin.getHeaders().get("Authorization").get(0);
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/{id}", 2L).header("Authorization", token))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnSatusCode403WithMockMvc() throws Exception {
        String token = headerTeste.getHeaders().get("Authorization").get(0);
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/{id}", 2L).header("Authorization", token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void createWhenNameIsNullSholdReturnStatusCode400() throws Exception {
        Fornecedor fornecedor = new Fornecedor(2L, null);
        BDDMockito.when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores", POST, new HttpEntity<>(fornecedor, headerTeste.getHeaders()), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("O nome é obrigatório");
    }

    @Test
    public void createSholdPersistDataAndReturnStatusCode201() throws Exception {
        Fornecedor fornecedor = new Fornecedor(2L, "Bernadete Clementino Pontes");
        BDDMockito.when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);
        ResponseEntity<Fornecedor> response = restTemplate.exchange("/fornecedores", POST, new HttpEntity<>(fornecedor, headerTeste.getHeaders()), Fornecedor.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Bernadete Clementino Pontes");
    }
}
