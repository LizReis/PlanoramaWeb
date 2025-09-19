package web.planorama.demo.mapping;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.RegistrarEstudoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T21:55:39-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class RegistrarEstudoMapperImpl implements RegistrarEstudoMapper {

    @Override
    public RegistrarEstudoEntity toRegistrarEstudoEntity(RegistrarEstudoDTO registrarEstudoDTO) {
        if ( registrarEstudoDTO == null ) {
            return null;
        }

        RegistrarEstudoEntity registrarEstudoEntity = new RegistrarEstudoEntity();

        registrarEstudoEntity.setId( registrarEstudoDTO.getId() );

        return registrarEstudoEntity;
    }

    @Override
    public RegistrarEstudoDTO toRegistrarEstudoDTO(RegistrarEstudoEntity registrarEstudoEntity) {
        if ( registrarEstudoEntity == null ) {
            return null;
        }

        RegistrarEstudoDTO registrarEstudoDTO = new RegistrarEstudoDTO();

        registrarEstudoDTO.setId( registrarEstudoEntity.getId() );

        return registrarEstudoDTO;
    }
}
