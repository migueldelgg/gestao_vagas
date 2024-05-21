package br.com.migueldelgado.gestao_vagas.modules.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJobDTO {

    @NotBlank
    private String description;
    private String benefits;

    @NotBlank(message = "Esse campo é obrigatório.")
    private String level;

}
