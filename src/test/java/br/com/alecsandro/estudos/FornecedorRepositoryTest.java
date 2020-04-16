package br.com.alecsandro.estudos;

import br.com.alecsandro.estudos.model.Fornecedor;
import br.com.alecsandro.estudos.repository.FornecedorRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FornecedorRepositoryTest {

    String nome = "Fornecedor Teste";

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Fornecedor fornecedor = new Fornecedor(nome);
        this.fornecedorRepository.save(fornecedor);
        assertThat(fornecedor.getId()).isNotNull();
        assertThat(fornecedor.getNome()).isEqualTo(nome);
    }

    @Test
    public void updateShouldChangeAndPersistData() {
        String nomeAlterado = "Nome alterado";
        Fornecedor fornecedor = new Fornecedor(nome);
        this.fornecedorRepository.save(fornecedor);
        fornecedor.setNome(nomeAlterado);
        this.fornecedorRepository.save(fornecedor);
        assertThat(fornecedor.getId()).isNotNull();
        assertThat(fornecedor.getNome()).isEqualTo(nomeAlterado);
    }

    @Test
    public void deleteShouldRemoveData() {
        Fornecedor fornecedor = new Fornecedor(nome);
        this.fornecedorRepository.save(fornecedor);
        fornecedorRepository.delete(fornecedor);
        assertThat(fornecedorRepository.findById(fornecedor.getId())).isEmpty();
    }

    @Test
    public void findByNomeIgnoreCaseContainingShouldIgnoreCase() {
        String nome2 = "fornecedor teste";
        Fornecedor fornecedor1 = new Fornecedor(nome);
        Fornecedor fornecedor2 = new Fornecedor(nome2);
        this.fornecedorRepository.save(fornecedor1);
        this.fornecedorRepository.save(fornecedor2);
        List<Fornecedor> fornecedores = fornecedorRepository.findByNomeIgnoreCaseContaining(nome2);
        assertThat(fornecedores.size()).isEqualTo(2);
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("O nome é obrigatório");
        this.fornecedorRepository.save(new Fornecedor());
    }
}
