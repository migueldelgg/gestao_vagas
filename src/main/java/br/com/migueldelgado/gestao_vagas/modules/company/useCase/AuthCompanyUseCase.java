package br.com.migueldelgado.gestao_vagas.modules.company.useCase;

import br.com.migueldelgado.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.migueldelgado.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Executa a autenticação de uma empresa.
     * @param authCompanyDTO O objeto AuthCompanyDTO contendo as credenciais da empresa para autenticação
     * @return O token JWT gerado para a empresa autenticada
     * @throws AuthenticationException se a autenticação falhar devido a credenciais inválidas
     * @throws UsernameNotFoundException se o nome de usuário não existir no sistema
     */
    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException, UsernameNotFoundException {

        // Verifica se o username existe.
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username/Password incorrect.")
        );

        // Verificar se as senhas são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        // Se não forem iguais, retorna erro.
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se forem iguais, gera o token JWT
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // Limite de tempo para expirar o token
                .withSubject(company.getId().toString())
                .sign(algorithm);
        return token;
    }
}
