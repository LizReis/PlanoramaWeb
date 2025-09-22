package web.planorama.demo.mapping;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.SessaoEstudoEntity;

@Mapper(componentModel = "spring", uses = { MateriaPlanejamentoMapper.class, MateriaMapper.class })
public interface SessaoEstudoMapper {
    @Mapping(source = "materiaPlanejamento.id", target = "idMateriaPlanejamento")
    @Mapping(source = "materiaPlanejamento.materiaEntity", target = "materiaDTO")
    public SessaoEstudoDTO toSessaoEstudoDTO(SessaoEstudoEntity sessaoEstudoEntity);

    @Mapping(source = "idMateriaPlanejamento", target = "materiaPlanejamento.id")
    @Mapping(target = "materiaPlanejamento.materiaEntity", source = "materiaDTO")
    public SessaoEstudoEntity toSessaoEstudoEntity(SessaoEstudoDTO sessaoEstudoDTO);
}
