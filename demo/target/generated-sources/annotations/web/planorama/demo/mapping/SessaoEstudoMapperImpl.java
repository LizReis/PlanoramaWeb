package web.planorama.demo.mapping;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.SessaoEstudoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-13T20:21:16-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SessaoEstudoMapperImpl implements SessaoEstudoMapper {

    @Autowired
    private MateriaMapper materiaMapper;
    @Autowired
    private PlanejamentoMapper planejamentoMapper;

    @Override
    public SessaoEstudoDTO toSessaoEstudoDTO(SessaoEstudoEntity sessaoEstudoEntity) {
        if ( sessaoEstudoEntity == null ) {
            return null;
        }

        SessaoEstudoDTO sessaoEstudoDTO = new SessaoEstudoDTO();

        sessaoEstudoDTO.setMateriaDTO( materiaMapper.toMateriaDTO( sessaoEstudoEntity.getMateriaEntity() ) );
        sessaoEstudoDTO.setPlanejamentoDTO( planejamentoMapper.toPlanejamentoDTO( sessaoEstudoEntity.getPlanejamentoEntity() ) );
        sessaoEstudoDTO.setDuracaoSessao( sessaoEstudoEntity.getDuracaoSessao() );
        sessaoEstudoDTO.setId( sessaoEstudoEntity.getId() );

        return sessaoEstudoDTO;
    }

    @Override
    public SessaoEstudoEntity toSessaoEstudoEntity(SessaoEstudoDTO sessaoEstudoDTO) {
        if ( sessaoEstudoDTO == null ) {
            return null;
        }

        SessaoEstudoEntity sessaoEstudoEntity = new SessaoEstudoEntity();

        sessaoEstudoEntity.setMateriaEntity( materiaMapper.toMateriaEntity( sessaoEstudoDTO.getMateriaDTO() ) );
        sessaoEstudoEntity.setPlanejamentoEntity( planejamentoMapper.toPlanejamentoEntity( sessaoEstudoDTO.getPlanejamentoDTO() ) );
        sessaoEstudoEntity.setDuracaoSessao( sessaoEstudoDTO.getDuracaoSessao() );
        sessaoEstudoEntity.setId( sessaoEstudoDTO.getId() );

        return sessaoEstudoEntity;
    }
}
