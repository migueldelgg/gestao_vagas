package br.com.migueldelgado.gestao_vagas.modules.company.repositories;

import br.com.migueldelgado.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    // "contains - LIKE "

    // "Select * from job where description LIKE %filter%
    List<JobEntity> findByDescriptionContaining(String filter);
}
