package br.com.migueldelgado.gestao_vagas.modules.company.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.UserFoundException;
import br.com.migueldelgado.gestao_vagas.modules.company.entity.CompanyEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity){

        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        return this.companyRepository.save(companyEntity);
    }
}
