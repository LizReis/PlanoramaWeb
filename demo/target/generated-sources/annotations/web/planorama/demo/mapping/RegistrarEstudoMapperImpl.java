package web.planorama.demo.mapping;

import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.RegistrarEstudoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-21T02:11:54-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class RegistrarEstudoMapperImpl implements RegistrarEstudoMapper {

    @Override
    public RegistrarEstudoEntity toRegistrarEstudoEntity(RegistrarEstudoDTO registrarEstudoDTO) {
        if ( registrarEstudoDTO == null ) {
            return null;
        }

        RegistrarEstudoEntity registrarEstudoEntity = new RegistrarEstudoEntity();

        registrarEstudoEntity.setAssunto( mapAssuntoIdToAssuntoEntity( registrarEstudoDTO.getAssuntoId() ) );
        registrarEstudoEntity.setMateria( mapMateriaIdToMateriaEntity( registrarEstudoDTO.getMateriaId() ) );
        registrarEstudoEntity.setPlanejamento( mapPlanejamentoIdToPlanejamentoEntity( registrarEstudoDTO.getPlanejamentoId() ) );

        registrarEstudoEntity.setDuracaoEmMinutos( registrarEstudoDTO.getHorasEstudadas() * 60 + registrarEstudoDTO.getMinutosEstudados() );

        return registrarEstudoEntity;
    }

    @Override
    public RegistrarEstudoDTO toRegistrarEstudoDTO(RegistrarEstudoEntity registrarEstudoEntity) {
        if ( registrarEstudoEntity == null ) {
            return null;
        }

        RegistrarEstudoDTO registrarEstudoDTO = new RegistrarEstudoDTO();

        registrarEstudoDTO.setAssuntoId( registrarEstudoEntityAssuntoId( registrarEstudoEntity ) );
        registrarEstudoDTO.setMateriaId( registrarEstudoEntityMateriaId( registrarEstudoEntity ) );
        registrarEstudoDTO.setId( registrarEstudoEntity.getId() );

        calcularHorasEMinutos( registrarEstudoDTO, registrarEstudoEntity );

        return registrarEstudoDTO;
    }

    private UUID registrarEstudoEntityAssuntoId(RegistrarEstudoEntity registrarEstudoEntity) {
        AssuntoEntity assunto = registrarEstudoEntity.getAssunto();
        if ( assunto == null ) {
            return null;
        }
        return assunto.getId();
    }

    private UUID registrarEstudoEntityMateriaId(RegistrarEstudoEntity registrarEstudoEntity) {
        MateriaEntity materia = registrarEstudoEntity.getMateria();
        if ( materia == null ) {
            return null;
        }
        return materia.getId();
    }
}
