package br.com.migueldelgado.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvolvedora Junior.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Vr, gympass, plano de saude.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "JR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
