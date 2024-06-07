package br.com.migueldelgado.gestao_vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object object){
        // Convertendo um objeto para Json pois o conteudo do mvc precisa ser um Json.
        try{
            final ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(object);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(UUID companyId, String secret){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // Limite de tempo para expirar o token
                .withSubject(companyId.toString())
                .sign(algorithm);

        return token;
    }
}


