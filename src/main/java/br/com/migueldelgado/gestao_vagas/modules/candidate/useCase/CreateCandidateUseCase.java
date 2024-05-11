package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import br.com.migueldelgado.gestao_vagas.exceptions.UserFoundException;
import br.com.migueldelgado.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Executa a lógica de criação de um novo candidato, verificando se já existe um usuário com o mesmo nome de usuário ou e-mail.
     * @param candidateEntity O objeto CandidateEntity contendo os dados do candidato a ser criado
     * @return O objeto CandidateEntity criado e persistido no banco de dados
     * @throws UserFoundException se um usuário com o mesmo nome de usuário ou e-mail já existir no sistema
     */
    public CandidateEntity execute(CandidateEntity candidateEntity) throws UserFoundException {
        // Verifica se já existe um usuário com o mesmo nome de usuário ou e-mail
        candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        // Salva o novo candidato no banco de dados

        var password = passwordEncoder.encode(candidateEntity.getPassword()); //faz a criptografia da senha do candidato
        candidateEntity.setPassword(password);

        return candidateRepository.save(candidateEntity);
    }
}