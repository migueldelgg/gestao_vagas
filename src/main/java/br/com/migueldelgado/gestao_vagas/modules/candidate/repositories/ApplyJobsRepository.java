package br.com.migueldelgado.gestao_vagas.modules.candidate.repositories;

import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplyJobsRepository extends JpaRepository<ApplyJobEntity, UUID> {

}
