package web.planorama.demo.mapping;

import org.mapstruct.Mapper;

import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.RegistrarEstudoEntity;


@Mapper(componentModel = "spring")
public interface RegistrarEstudoMapper {

    public RegistrarEstudoEntity toRegistrarEstudoEntity(RegistrarEstudoDTO registrarEstudoDTO);

    public RegistrarEstudoDTO toRegistrarEstudoDTO(RegistrarEstudoEntity registrarEstudoEntity);
}
