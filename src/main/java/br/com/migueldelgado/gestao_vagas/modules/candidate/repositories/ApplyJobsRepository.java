package br.com.migueldelgado.gestao_vagas.modules.candidate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobsRepository extends JpaRepository<ApplyJobsRepository, UUID> {

}
