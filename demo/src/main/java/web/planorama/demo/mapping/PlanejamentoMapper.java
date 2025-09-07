package web.planorama.demo.mapping;

import org.mapstruct.Mapper;

import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.PlanejamentoEntity;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class})
public interface PlanejamentoMapper {
    

    public PlanejamentoEntity toPlanejamentoEntity(PlanejamentoDTO planejamentoDTO);

    public PlanejamentoDTO toPlanejamentoDTO(PlanejamentoEntity planejamentoEntity);
}
