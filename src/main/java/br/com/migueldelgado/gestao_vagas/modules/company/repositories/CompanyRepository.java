package br.com.migueldelgado.gestao_vagas.modules.company.repositories;

import br.com.migueldelgado.gestao_vagas.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {

    /**
     * Busca uma empresa pelo nome de usuário ou e-mail.
     * @param username O nome de usuário da empresa a ser buscada
     * @param email O e-mail da empresa a ser buscada
     * @return Um objeto Optional contendo a empresa encontrada, ou vazio se não encontrada
     */
    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);

    /**
     * Busca uma empresa pelo nome de usuário.
     * @param username O nome de usuário da empresa a ser buscada
     * @return Um objeto Optional contendo a empresa encontrada, ou vazio se não encontrada
     */
    Optional<CompanyEntity> findByUsername(String username);
}