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

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("Username/Password incorrect.")
        );

        // Verificar se senha sao iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        //Se nao for igual -> Retorna erro.
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Se for igual -> Gerar token
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .sign(algorithm);
        return token;
    }
}