package web.planorama.demo.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;

public record MateriaDTO(UUID id, 
                @NotBlank
                @NotNull
                String nomeMateria, 
                UsuarioEntity criadoPor, 
                List<AssuntoEntity> listaAssuntos,
                List<MateriaPlanejamentoEntity> planejamentosComMateria) {

}
