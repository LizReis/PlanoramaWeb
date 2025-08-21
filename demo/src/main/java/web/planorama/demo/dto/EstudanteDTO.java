package web.planorama.demo.dto;

import java.util.UUID;

public record EstudanteDTO(UUID id, String nome, String email, String senha, String fotoUsuario, String descricaoUsuario) {

}
