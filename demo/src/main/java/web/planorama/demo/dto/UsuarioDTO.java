package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDTO(
            UUID id, 
            @NotBlank
            @NotNull
            String nome, 
            @NotBlank
            @NotNull
            String email, 
            @NotBlank
            @NotNull
            String senha, 
            String fotoUsuario, 
            String descricaoUsuario) {
}
