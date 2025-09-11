package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.entity.AssuntoEntity;

@Mapper(componentModel = "spring")
public interface AssuntoMapper {
    public AssuntoEntity toAssuntoEntity(AssuntoDTO assuntoDTO);

    public AssuntoDTO toAssuntoDTO(AssuntoEntity assuntoEntity);
}
