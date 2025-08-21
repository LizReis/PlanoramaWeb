package web.planorama.demo.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.planorama.demo.entity.AdministradorEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.repository.UsuarioRepository;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        // Define o "papel" (ROLE) do usuário para autorização futura
        String role = (usuario instanceof AdministradorEntity) ? "ADMIN" : "USER";

        return User.builder()
            .username(usuario.getEmail())
            .password(usuario.getSenha()) // A senha já deve estar criptografada no banco
            .roles(role)
            .build();
    }
}