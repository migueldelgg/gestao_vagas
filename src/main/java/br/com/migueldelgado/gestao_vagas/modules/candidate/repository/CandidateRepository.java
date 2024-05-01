package br.com.migueldelgado.gestao_vagas.modules.candidate.repository;

import br.com.migueldelgado.gestao_vagas.modules.candidate.entity.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {


}
