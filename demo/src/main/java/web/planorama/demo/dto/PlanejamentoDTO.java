package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import web.planorama.demo.entity.UsuarioEntity;

import java.util.ArrayList;

public record PlanejamentoDTO(UUID id, 
                    @NotBlank
                    @NotNull
                    String nomePlanejamento,
                    @NotBlank
                    @NotNull
                    String cargo, 
                    @NotBlank
                    @NotNull
                    int anoAplicacao, 
                    @NotNull
                    ArrayList<String> disponibilidade,
                    @NotBlank
                    @NotNull 
                    int horasDiarias,
                    @NotNull 
                    ArrayList<String> materias,
                    @NotNull 
                    UsuarioEntity criador,
                    @NotNull 
                    boolean planoArquivado, 
                    @NotNull
                    boolean preDefinidoAdm) {

}
