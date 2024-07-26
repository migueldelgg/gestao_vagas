package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.migueldelgado.gestao_vagas.exceptions.UserFoundException;
import br.com.migueldelgado.gestao_vagas.exceptions.UserNotFoundException;
import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.migueldelgado.gestao_vagas.utils.TestUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CreateCandidateUseCaseTest {

    @InjectMocks
    private CreateCandidateUseCase createCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PasswordEncoder encoder;

    @Test
    @DisplayName("should not be possible to create a candidate that already exists")
    public void should_not_be_possible_to_create_a_candidate_that_already_exists() {

        var candidate = CandidateEntity.builder()
                .id(UUID.randomUUID())
                .name("Marcelo")
                .username("marcelinho")
                .email("marcelinho@gmail.com")
                .password(TestUtils.generateRandomNumberString(10))
                .description("javadev")
                .curriculum(null)
                .createdAt(LocalDateTime.now())
                .build();

        // Mockando o comportamento do repositório
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        when(candidateRepository.findByUsernameOrEmail("Marcelo", "marcelinho@gmail.com"))
                .thenReturn(java.util.Optional.of(candidate));

        try {
            createCandidateUseCase.execute(candidate);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserFoundException.class);
        }
    }

}
