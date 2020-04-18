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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FornecedorEndpointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    private FornecedorRepository fornecedorRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("admin", "nimda");
        }
    }

    @Before
    public void setup() {
        Fornecedor fornecedor = new Fornecedor(1L, "Almir Armando Pontes");
        BDDMockito.when(fornecedorRepository.findById(1L)).thenReturn(java.util.Optional.of(fornecedor));
    }

    @Test
    public void listFornecedoresWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("abc", "123");
        ResponseEntity<String> response = restTemplate.getForEntity("/fornecedores", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void getFornecedoresByIdWhenUserNameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        restTemplate = restTemplate.withBasicAuth("abc", "123");
        ResponseEntity<String> response = restTemplate.getForEntity("/fornecedores/1", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listFornecedoresWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Fornecedor> fornecedores = asList(new Fornecedor(1L, "Almir Armando Pontes"),
                new Fornecedor(2L, "Bernadete Clementino Pontes"));

        BDDMockito.when(fornecedorRepository.findAll()).thenReturn(fornecedores);

//        restTemplate = restTemplate.withBasicAuth("teste", "1234567");
        ResponseEntity<String> response = restTemplate.getForEntity("/fornecedores", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getFornecedoresByIdWhenUserNameAndPasswordAreCorrectShouldReturnStatusCode200() {
        restTemplate = restTemplate.withBasicAuth("teste", "1234567");
        ResponseEntity<Fornecedor> response = restTemplate.getForEntity("/fornecedores/{id}", Fornecedor.class, 1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getFornecedoresByIdWhenUserNameAndPasswordAreCorrectAndFornecedorDoesNotExistsShouldReturnStatusCode404() {
        restTemplate = restTemplate.withBasicAuth("teste", "1234567");
        ResponseEntity<Fornecedor> response = restTemplate.getForEntity("/fornecedores/{id}", Fornecedor.class, 2);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndFornecedorDoesNotExistsShouldReturnSatusCode404() {
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        ResponseEntity<String> response = restTemplate.exchange("/fornecedores/{id}", DELETE, null, String.class, 2L);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @WithMockUser(username = "aa", password = "aa", roles = "ADMIN")
    public void deleteWhenUserHasRoleAdminAndFornecedorDoesNotExistsShouldReturnSatusCode404WithMockMvc() throws Exception {
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/{id}", 2L)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "aa", password = "aa", roles = "USER")
    public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnSatusCode403WithMockMvc() throws Exception {
        BDDMockito.doNothing().when(fornecedorRepository).delete(new Fornecedor(2L, "Teste"));
        mockMvc.perform(MockMvcRequestBuilders.delete("/fornecedores/{id}", 2L)).andExpect(status().isForbidden());
    }

    @Test
    public void createWhenNameIsNullSholdReturnStatusCode400() throws Exception {
        Fornecedor fornecedor = new Fornecedor(2L, null);
        BDDMockito.when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);
        ResponseEntity<String> response = restTemplate.postForEntity("/fornecedores", fornecedor, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody()).contains("O nome é obrigatório");
    }

    @Test
    public void createSholdPersistDataAndReturnStatusCode201() throws Exception {
        Fornecedor fornecedor = new Fornecedor(2L, "Bernadete Clementino Pontes");
        BDDMockito.when(fornecedorRepository.save(fornecedor)).thenReturn(fornecedor);
        ResponseEntity<Fornecedor> response = restTemplate.postForEntity("/fornecedores", fornecedor, Fornecedor.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(201);
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Bernadete Clementino Pontes");
    }
}
