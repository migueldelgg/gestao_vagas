package br.com.migueldelgado.gestao_vagas.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class TestUtils {

    private static final Random RANDOM = new Random();

    public static String generateRandomNumberString(int digits) {
        long minValue = (long) Math.pow(10, digits - 1);
        long maxValue = (long) Math.pow(10, digits) - 1;
        long randomNumber = minValue + (long) (RANDOM.nextDouble() * (maxValue - minValue));
        return Long.toString(randomNumber);
    }

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


