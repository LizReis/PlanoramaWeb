package web.planorama.demo.mapping;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T11:51:41-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class MateriaPlanejamentoMapperImpl implements MateriaPlanejamentoMapper {

    @Override
    public MateriaPlanejamentoEntity toMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO) {
        if ( materiaPlanejamentoDTO == null ) {
            return null;
        }

        MateriaPlanejamentoEntity materiaPlanejamentoEntity = new MateriaPlanejamentoEntity();

        if ( materiaPlanejamentoDTO.getNivelConhecimento() != null ) {
            materiaPlanejamentoEntity.setNivelConhecimento( materiaPlanejamentoDTO.getNivelConhecimento() );
        }
        else {
            materiaPlanejamentoEntity.setNivelConhecimento( 0 );
        }
        if ( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() != null ) {
            materiaPlanejamentoEntity.setCargaHorariaMateriaPlano( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() );
        }
        else {
            materiaPlanejamentoEntity.setCargaHorariaMateriaPlano( 0 );
        }
        materiaPlanejamentoEntity.setId( materiaPlanejamentoDTO.getId() );

        return materiaPlanejamentoEntity;
    }

    @Override
    public MateriaPlanejamentoDTO toMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity) {
        if ( materiaPlanejamentoEntity == null ) {
            return null;
        }

        MateriaPlanejamentoDTO materiaPlanejamentoDTO = new MateriaPlanejamentoDTO();

        materiaPlanejamentoDTO.setId( materiaPlanejamentoEntity.getId() );
        materiaPlanejamentoDTO.setNivelConhecimento( materiaPlanejamentoEntity.getNivelConhecimento() );
        materiaPlanejamentoDTO.setCargaHorariaMateriaPlano( materiaPlanejamentoEntity.getCargaHorariaMateriaPlano() );

        return materiaPlanejamentoDTO;
    }
}
