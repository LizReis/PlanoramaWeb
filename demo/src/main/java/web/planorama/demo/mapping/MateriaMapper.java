package web.planorama.demo.mapping;

import org.mapstruct.Mapper;

import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.MateriaEntity;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class})
public interface MateriaMapper {

    public MateriaEntity toMateriaEntity(MateriaDTO materiaDTO);

    public MateriaDTO toMateriaDTO(MateriaEntity materiaEntity);
}
