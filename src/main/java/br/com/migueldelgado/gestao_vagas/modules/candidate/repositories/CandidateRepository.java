package br.com.migueldelgado.gestao_vagas.modules.candidate.repositories;

import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

    /**
     * Utilizando das vantagens do spring para fazer um método que retorna uma busca por Username ou Email.
     * sintaxe -> findBy Atributo Condicional(or, and) Atributo
     */
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);


}
