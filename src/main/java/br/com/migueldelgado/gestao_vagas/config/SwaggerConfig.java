package br.com.migueldelgado.gestao_vagas.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean //sobreescrevendo uma implementacao que ja existe
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Gestão de vagas").
                        description("API responsavel pela gestão de vagas.").
                        version("1")).
                schemaRequirement("jwt_auth", createSecurityScheme());
        /*
        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
        .components(new Components().addSecuritySchemes("Bearer Authentication",createSecurityScheme()));
        Caso todas as rotas precisem de autenticacao usar esse swagger config.
        */
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name("jwt_auth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }

}
