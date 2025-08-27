package web.planorama.demo.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.UsuarioEntity;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity);

    EstudanteDTO toEstudanteDTO(EstudanteEntity estudanteEntity);
    AdministradorDTO toAdministradorDTO(AdministradorEntity administradorEntity);

    @Mapping(target = "id", source = "estudanteDTO.id")
    EstudanteEntity toEstudanteEntity(EstudanteDTO estudanteDTO);
    
    @Mapping(target = "id", source = "administradorDTO.id")
    AdministradorEntity toAdministradorEntity(AdministradorDTO administradorDTO);
}
