package br.com.migueldelgado.gestao_vagas.modules.company.repository;

import br.com.migueldelgado.gestao_vagas.modules.company.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {


}
