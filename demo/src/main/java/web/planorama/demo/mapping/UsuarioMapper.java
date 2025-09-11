package web.planorama.demo.mapping;

import java.util.List;

import org.mapstruct.Mapper;

import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.PapelEntity;
import web.planorama.demo.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    public UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity);

    public UsuarioEntity toUsuarioEntity(UsuarioDTO UsuarioDTO, List<PapelEntity> papeis);

}
