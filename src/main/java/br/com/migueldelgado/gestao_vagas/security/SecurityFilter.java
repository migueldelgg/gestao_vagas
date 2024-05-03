package br.com.migueldelgado.gestao_vagas.security;

import br.com.migueldelgado.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JWTProvider jwtProvider;

    /**
     * Filtro de segurança para validar tokens JWT e configurar a autenticação do Spring Security.
     * @param request O objeto HttpServletRequest
     * @param response O objeto HttpServletResponse
     * @param filterChain O objeto FilterChain para encadear os filtros
     * @throws ServletException se ocorrer um erro de servlet
     * @throws IOException se ocorrer um erro de I/O
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtém o cabeçalho Authorization da requisição
        String header = request.getHeader("Authorization");

        // Limpa qualquer autenticação anterior do contexto de segurança
        SecurityContextHolder.getContext().setAuthentication(null);

        // Verifica se o cabeçalho Authorization está presente na requisição
        if(header != null){

            // Valida o token JWT e extrai o subject (company_id) do token
            var subjectToken = this.jwtProvider.validateToken(header);

            // Se a validação do token falhar, retorna um status de não autorizado
            if (subjectToken.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // Se a validação do token for bem-sucedida, configura a autenticação do Spring Security
            else {
                // Adiciona o company_id como atributo da requisição
                request.setAttribute("company_id", subjectToken);
                // Configura a autenticação com o subject (company_id) do token
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // Encaminha a requisição para o próximo filtro na cadeia
        filterChain.doFilter(request, response);

    }
}
