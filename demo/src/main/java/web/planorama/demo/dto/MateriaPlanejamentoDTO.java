package web.planorama.demo.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.PlanejamentoEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPlanejamentoDTO {

    private UUID id;

    private PlanejamentoEntity planejamentoEntity;
    private MateriaEntity materiaEntity;

    private int nivelConhecimento;

    private int cargaHorariaSemanal;
    private int tempoSessao;
}