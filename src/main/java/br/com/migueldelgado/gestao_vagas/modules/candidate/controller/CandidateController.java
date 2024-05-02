package br.com.migueldelgado.gestao_vagas.modules.candidate.controller;

import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.useCase.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    CreateCandidateUseCase createCandidateUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{
            var result = createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
