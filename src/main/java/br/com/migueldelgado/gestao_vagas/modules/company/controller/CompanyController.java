package br.com.migueldelgado.gestao_vagas.modules.company.controller;

import br.com.migueldelgado.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.entities.JobEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.useCase.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Informações da empresa.")
public class CompanyController {

    @Autowired
    CreateCompanyUseCase createCompanyUseCase;

    /**
     * Endpoint para criar uma nova empresa.
     * @param companyEntity O objeto CompanyEntity contendo os dados da empresa a ser criada
     * @return ResponseEntity com o status HTTP e, em caso de sucesso, os dados da empresa criada
     */
    @PostMapping("/")
    @Operation(summary = "Cadastro de empresas.", description = "Essa função é responsavel por cadastrar empresas dentro da aplicação.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema= @Schema(implementation = JobEntity.class))
            })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
        try{
            var result = this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
