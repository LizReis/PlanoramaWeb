package web.planorama.demo.dto;

import java.util.UUID;

import web.planorama.demo.entity.UsuarioEntity;

public record MateriaDTO(UUID id, String nomeMateria, UsuarioEntity criadoPor) {

}
