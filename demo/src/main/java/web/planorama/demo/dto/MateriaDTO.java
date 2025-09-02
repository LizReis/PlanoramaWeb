package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import web.planorama.demo.entity.UsuarioEntity;

public record MateriaDTO(UUID id,
                         @NotBlank
                         @NotNull
                         String nomeMateria,
                         int cargaHorariaSemanal,
                         int proficiencia,
                         int tempoSessao) {

}
