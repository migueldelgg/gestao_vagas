package br.com.migueldelgado.gestao_vagas.modules.company.controller;

import br.com.migueldelgado.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.migueldelgado.gestao_vagas.modules.company.entities.JobEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.useCase.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controlador REST para operações relacionadas a vagas de emprego.
 */
@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    /**
     * Método para criar uma nova vaga de emprego.
     *
     * @param createJobDTO Objeto de transferência de dados com informações da vaga a ser criada.
     * @param request      Requisição HTTP contendo o ID da empresa que está criando a vaga.
     * @return A entidade da vaga de emprego criada.
     */
    @PostMapping("/")
    @Tag(name = "Vagas", description = "Informações das vagas.")
    @Operation(summary = "Cadastro de vagas.", description = "Essa função é responsavel por cadastrar as vagas dentro da empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema= @Schema(implementation = JobEntity.class))
            })
    })
    public JobEntity create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request){
        // Obtém o ID da empresa da requisição
        var companyId = request.getAttribute("company_id");

        // Cria uma nova entidade de vaga de emprego com base nos dados recebidos na requisição
        var jobEntity = JobEntity.builder()
                .benefits(createJobDTO.getBenefits()) // Define os benefícios da vaga de emprego
                .level(createJobDTO.getLevel()) // Define o nível da vaga de emprego
                .companyId(UUID.fromString(companyId.toString())) // Converte o companyId de String para UUID
                .description(createJobDTO.getDescription())
                .build(); // Finaliza a construção da entidade de vaga de emprego

        // Executa o caso de uso para criar a vaga de emprego, passando a nova entidade como argumento, e retorna a entidade criada
        return createJobUseCase.execute(jobEntity);
    }
}
