package web.planorama.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.planorama.demo.entity.PlanejamentoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPlanejamentoDTO {
    private UUID id;
    private PlanejamentoDTO planejamentoEntity;
    private MateriaDTO materiaEntity;
    @NotNull
    @Min(value = 1, message = "O valor deve ser no mínimo 1.")
    @Max(value = 5, message = "O valor deve ser no máximo 5.")
    private int nivelConhecimento;
    @NotNull(message = "A carga horária é obrigatória.")
    @Min(value = 1, message = "A carga horária deve ser de no mínimo 1 hora.")
    private int cargaHorariaMateriaPlano;
}
