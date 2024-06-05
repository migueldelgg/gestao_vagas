package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.JobNotFoundException;
import br.com.migueldelgado.gestao_vagas.exceptions.UserNotFoundException;
import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock // identifica que a classe JobRepository é uma dependencia que esta presente dentro da classe ApplyJobCandidateUseCase
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should Not be able to apply job with candidate not found.")
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try{
            applyJobCandidateUseCase.execute(null, null);
        }catch (Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Should Not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found(){

        var idCandidate = UUID.randomUUID();

        var candidate = new CandidateEntity();

        candidate.setId(idCandidate);

        // Quando for chamado findById vai retornar um objeto candidate ->
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        try{
            applyJobCandidateUseCase.execute(idCandidate, null);
        }catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

}
