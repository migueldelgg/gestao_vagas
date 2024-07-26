package br.com.migueldelgado.gestao_vagas.modules.candidate.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyJobResponse {

    private String message;
    private String jobDescription;
    private String candidateName;
    private LocalDateTime date;
}
