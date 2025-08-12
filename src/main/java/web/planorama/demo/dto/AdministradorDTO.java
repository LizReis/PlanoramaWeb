package web.planorama.demo.dto;

import java.util.UUID;

public record AdministradorDTO(UUID id, String nome, String email, String senha, String foto, String descricao) {
}
