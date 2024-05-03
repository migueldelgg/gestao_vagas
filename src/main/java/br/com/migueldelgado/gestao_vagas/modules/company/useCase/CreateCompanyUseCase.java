package br.com.migueldelgado.gestao_vagas.modules.company.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.UserFoundException;
import br.com.migueldelgado.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Executa a criação de uma nova empresa.
     * @param companyEntity O objeto CompanyEntity contendo os dados da empresa a ser criada
     * @return O objeto CompanyEntity criado e persistido no banco de dados
     * @throws UserFoundException se uma empresa com o mesmo nome de usuário ou e-mail já existir no sistema
     */
    public CompanyEntity execute(CompanyEntity companyEntity) throws UserFoundException {

        // Verifica se já existe uma empresa com o mesmo nome de usuário ou e-mail
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        // Codifica a senha antes de salvar no banco de dados
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        // Salva a nova empresa no banco de dados
        return this.companyRepository.save(companyEntity);
    }
}
