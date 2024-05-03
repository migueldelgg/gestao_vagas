package br.com.migueldelgado.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    /**
     * Valida um token JWT.
     * @param token O token JWT a ser validado
     * @return O assunto (subject) extraído do token se a validação for bem-sucedida, ou uma string vazia se a validação falhar
     */
    public String validateToken(String token){
        token = token.replace("Bearer ", ""); // Remove o prefixo "Bearer " do token

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            // Verifica e extrai o assunto (subject) do token
            var subject = JWT.require(algorithm)
                    .build()
                    .verify(token)
                    .getSubject();

            return subject;
        }catch (JWTVerificationException E){
            // Em caso de falha na validação, imprime o stack trace e retorna uma string vazia
            E.printStackTrace();

            return "";
        }
    }
}
