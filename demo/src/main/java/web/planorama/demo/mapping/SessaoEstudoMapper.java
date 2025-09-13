package web.planorama.demo.mapping;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.SessaoEstudoEntity;

@Mapper(componentModel = "spring", uses = { MateriaMapper.class, PlanejamentoMapper.class })
public interface SessaoEstudoMapper {
    @Mapping(source = "materiaEntity", target = "materiaDTO")
    @Mapping(source = "planejamentoEntity", target = "planejamentoDTO")
    public SessaoEstudoDTO toSessaoEstudoDTO(SessaoEstudoEntity sessaoEstudoEntity);

    @Mapping(source = "materiaDTO", target = "materiaEntity")
    @Mapping(source = "planejamentoDTO", target = "planejamentoEntity")
    public SessaoEstudoEntity toSessaoEstudoEntity(SessaoEstudoDTO sessaoEstudoDTO);
}
