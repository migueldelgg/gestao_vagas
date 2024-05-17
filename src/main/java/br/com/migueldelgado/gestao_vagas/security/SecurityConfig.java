package br.com.migueldelgado.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter candidateFilter;

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };
    /**
     * Configuração de segurança para a aplicação.
     * @param http O objeto HttpSecurity para configurar as regras de segurança
     * @return Um objeto SecurityFilterChain contendo as configurações de segurança
     * @throws Exception se ocorrer um erro durante a configuração
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/candidate/").permitAll()
                            .requestMatchers("/company/").permitAll()
                            .requestMatchers("/company/auth").permitAll()
                            .requestMatchers("/candidate/auth").permitAll()
                            .requestMatchers(SWAGGER_LIST).permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(candidateFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class); //Cria um filtro e pede pro spring security passe pelo filtro

        return http.build();
    }

    /**
     * Cria um bean para o PasswordEncoder usado na autenticação.
     * @return Um objeto PasswordEncoder para codificar e verificar senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
