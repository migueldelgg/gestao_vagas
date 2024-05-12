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

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    /**
     * Método para autenticar um candidato com base nas credenciais fornecidas.
     *
     * @param authCandidateRequestDTO Objeto de transferência de dados contendo as credenciais do candidato.
     * @return Objeto de transferência de dados contendo o token de acesso gerado para o candidato autenticado.
     * @throws AuthenticationException Se as credenciais do candidato forem inválidas ou não puderem ser autenticadas.
     */
    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {

        // Busca o candidato no repositório pelo nome de usuário
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username/Password incorrect"));

        // Verifica se a senha fornecida corresponde à senha do candidato no banco de dados
        var passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDTO.password(), candidate.getPassword());

        // Se as senhas não corresponderem, lança uma exceção de autenticação
        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        // Configura o algoritmo e cria um token JWT para o candidato autenticado
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

        var token = JWT.create()
                .withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expiresIn)
                .sign(algorithm);

        // Constrói e retorna a resposta de autenticação contendo o token de acesso
        var authCandidateResponse = AuthCandidateResponseDTO.builder()
                .acess_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .build();

        return authCandidateResponse;
    }

}
