package br.com.migueldelgado.gestao_vagas.modules.company.controller;

import br.com.migueldelgado.gestao_vagas.modules.company.entity.JobEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.useCase.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    public JobEntity create(@Valid @RequestBody JobEntity jobEntity){
        return createJobUseCase.execute(jobEntity);
    }
}
