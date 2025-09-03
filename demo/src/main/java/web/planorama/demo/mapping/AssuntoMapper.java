package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.entity.AssuntoEntity;

@Component
public class AssuntoMapper {
    public AssuntoEntity toAssuntoEntity(AssuntoDTO assuntoDTO){
        return new AssuntoEntity(assuntoDTO.getId(), assuntoDTO.getNomeAssunto(), assuntoDTO.getMateriaEntity());
    }

    public AssuntoDTO toAssuntoDTO(AssuntoEntity assuntoEntity){
        return new AssuntoDTO(assuntoEntity.getId(), assuntoEntity.getNomeAssunto(), assuntoEntity.getMateriaEntity());
    }
}
