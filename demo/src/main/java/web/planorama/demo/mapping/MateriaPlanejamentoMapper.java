package web.planorama.demo.mapping;


import org.springframework.stereotype.Component;

import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;

@Component
public class MateriaPlanejamentoMapper {
    public MateriaPlanejamentoEntity toMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO){
        return new MateriaPlanejamentoEntity(materiaPlanejamentoDTO.getId(), materiaPlanejamentoDTO.getPlanejamentoEntity(), materiaPlanejamentoDTO.getMateriaEntity(), materiaPlanejamentoDTO.getNivelConhecimento(), materiaPlanejamentoDTO.getCargaHorariaMateriaPlano());
    }

    public MateriaPlanejamentoDTO toMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity){
        return new MateriaPlanejamentoDTO(materiaPlanejamentoEntity.getId(),materiaPlanejamentoEntity.getPlanejamentoEntity(), materiaPlanejamentoEntity.getMateriaEntity(), materiaPlanejamentoEntity.getNivelConhecimento(), materiaPlanejamentoEntity.getCargaHorariaMateriaPlano());
    }
}
