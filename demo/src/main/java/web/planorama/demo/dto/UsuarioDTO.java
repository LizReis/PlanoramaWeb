package web.planorama.demo.dto;

import java.util.UUID;

public record UsuarioDTO(UUID id, String nome, String email, String fotoUsuario, String descricaoUsuario) {
}
