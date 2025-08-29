package web.planorama.demo.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.UsuarioEntity;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity);

    //O SubclassMapping é utilizado para caso ele precise mapear de UsuarioDTO para UsuarioEntity
    //Mas no nosso caso não é possível porque UsuarioEntity é uma classe abstrata, então utilizamos
    //o subclassMapping para ver quem é o alvo e mandar para o target
    //@SubclassMapping(source = EstudanteDTO.class, target = EstudanteEntity.class)
    //@SubclassMapping(source = AdministradorDTO.class, target = AdministradorEntity.class)
    //UsuarioEntity toUsuarioEntity(UsuarioDTO usuarioDTO);

    EstudanteDTO toEstudanteDTO(EstudanteEntity estudanteEntity);
    AdministradorDTO toAdministradorDTO(AdministradorEntity administradorEntity);

    @Mapping(target = "id", source = "estudanteDTO.id")
    EstudanteEntity toEstudanteEntity(EstudanteDTO estudanteDTO);
    
    @Mapping(target = "id", source = "administradorDTO.id")
    AdministradorEntity toAdministradorEntity(AdministradorDTO administradorDTO);
}
