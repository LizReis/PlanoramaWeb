package web.planorama.demo.mapping;

import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.SessaoEstudoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-17T21:55:39-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
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
        sessaoEstudoDTO.setId( sessaoEstudoEntity.getId() );
        sessaoEstudoDTO.setDuracaoSessao( sessaoEstudoEntity.getDuracaoSessao() );

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
        sessaoEstudoEntity.setId( sessaoEstudoDTO.getId() );
        sessaoEstudoEntity.setDuracaoSessao( sessaoEstudoDTO.getDuracaoSessao() );

        return sessaoEstudoEntity;
    }
}
