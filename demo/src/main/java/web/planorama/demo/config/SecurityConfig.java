package web.planorama.demo.config;

// Removido o import do "java.beans.Customizer", pois não será mais usado
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Inicia pelas configurações de CSRF e Headers para o H2
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

            // Agora, define TODAS as regras de autorização de uma só vez
            .authorizeHttpRequests(authorize -> authorize
                // Libera o acesso para o H2 Console
                .requestMatchers("/h2-console/**").permitAll()
                // Libera o acesso a recursos estáticos (CSS, JS, imagens)
                .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/uploadsUser/**").permitAll()
                // Libera o acesso às páginas de login e cadastro
                .requestMatchers("/login", "/cadastro", "/cadastro/**").permitAll()
                // Exige autenticação para QUALQUER outra requisição
                .anyRequest().authenticated()
            )
            // Configura o formulário de login
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Configura o logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}