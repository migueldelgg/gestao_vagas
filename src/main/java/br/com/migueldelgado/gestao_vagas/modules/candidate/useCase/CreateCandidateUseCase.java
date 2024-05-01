package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.UserFoundException;
import br.com.migueldelgado.gestao_vagas.modules.candidate.entity.CandidateEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository.
                findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        return candidateRepository.save(candidateEntity);
    }
}
