package br.com.migueldelgado.gestao_vagas.modules.company.controller;

import br.com.migueldelgado.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.migueldelgado.gestao_vagas.modules.company.entities.JobEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.useCase.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Informações da empresa.")
public class AuthCompanyController {

    @Autowired
    AuthCompanyUseCase authCompanyUseCase;

    /**
     * Endpoint para autenticação de empresas.
     * @param authCompanyDTO O objeto AuthCompanyDTO contendo as credenciais da empresa para autenticação
     * @return ResponseEntity com o status HTTP e, em caso de sucesso, os dados da empresa autenticada
     * @throws AuthenticationException se a autenticação falhar
     */


    @Operation(summary = "Requisição de autorização.", description = "Essa função é responsavel por autorizar empresas cadastradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema= @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        try{
            var result = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }
}
