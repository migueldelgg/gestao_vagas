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

    /**
     * Endpoint para criar um novo candidato com validação de entrada e tratamento de exceções.
     * @param candidateEntity O objeto CandidateEntity contendo os dados do candidato a ser criado
     * @return ResponseEntity com o status HTTP e, em caso de sucesso, os dados do candidato criado
     */
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
