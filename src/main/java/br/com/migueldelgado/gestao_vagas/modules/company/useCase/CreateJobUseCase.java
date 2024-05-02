package br.com.migueldelgado.gestao_vagas.modules.company.useCase;

import br.com.migueldelgado.gestao_vagas.modules.company.entities.JobEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity){
        return this.jobRepository.save(jobEntity);
    }
}
