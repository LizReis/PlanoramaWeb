package web.planorama.demo.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Pega a lista de "papéis" (roles) do usuário autenticado.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Itera sobre os papéis para encontrar a role principal.
        for (GrantedAuthority authority : authorities) {
            // Se o papel for "ROLE_ADMIN"...
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                // ...redireciona para a home do administrador.
                response.sendRedirect("/homeAdm");
                return; // Encerra a execução para não haver mais redirecionamentos.
            }
            // Se o papel for "ROLE_USER"...
            else if (authority.getAuthority().equals("ROLE_USER")) {
                // ...redireciona para a home do estudante.
                response.sendRedirect("/home");
                return; // Encerra a execução.
            }
        }

        // Se por algum motivo o usuário não tiver um papel conhecido, redireciona para o login.
        response.sendRedirect("/login?error");
    }
}