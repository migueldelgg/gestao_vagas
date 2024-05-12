package br.com.migueldelgado.gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {


    private String name;
    private String description;
    private String username;
    private String email;
    private UUID id;


}
