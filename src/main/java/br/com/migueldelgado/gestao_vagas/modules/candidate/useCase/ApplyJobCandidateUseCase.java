package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.JobNotFoundException;
import br.com.migueldelgado.gestao_vagas.exceptions.UserNotFoundException;
import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.ApplyJobsRepository;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ApplyJobsRepository applyJobsRepository;

    //ID do candidato
    //ID da vaga
    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {

        //Validar se o candidato existe
        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
            throw new UserNotFoundException();
        });

        // Validar se a vaga existe
        this.jobRepository.findById(idJob).orElseThrow(() -> {
            throw new JobNotFoundException();
        });

        //Candidato se inscrever na vaga
        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate)
                .jobId(idJob).build();

        return applyJobsRepository.save(applyJob);
    }

}
