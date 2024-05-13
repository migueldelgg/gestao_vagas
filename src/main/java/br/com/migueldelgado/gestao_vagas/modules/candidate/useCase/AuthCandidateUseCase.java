package br.com.migueldelgado.gestao_vagas.modules.candidate.useCase;

import br.com.migueldelgado.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.migueldelgado.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.migueldelgado.gestao_vagas.modules.candidate.repositories.CandidateRepository;
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
import java.util.Arrays;

/**
 * Caso de uso para autenticação de candidatos.
 */
@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Método para autenticar um candidato com base nas credenciais fornecidas.
     *
     * @param authCandidateRequestDTO Objeto de transferência de dados contendo as credenciais do candidato.
     * @return Objeto de transferência de dados contendo o token de acesso gerado para o candidato autenticado.
     * @throws AuthenticationException Se as credenciais do candidato forem inválidas ou não puderem ser autenticadas.
     */
    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
            throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/password incorrect");
                });

        var passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        var AuthCandidateResponse = AuthCandidateResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return AuthCandidateResponse;
    }
}
