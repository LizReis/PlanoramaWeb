package web.planorama.demo.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.SessaoEstudoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T20:00:35-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class PlanejamentoMapperImpl implements PlanejamentoMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public PlanejamentoEntity toPlanejamentoEntity(PlanejamentoEntity planejamentoDTO) {
        if ( planejamentoDTO == null ) {
            return null;
        }

        PlanejamentoEntity planejamentoEntity = new PlanejamentoEntity();

        planejamentoEntity.setId( planejamentoDTO.getId() );
        planejamentoEntity.setNomePlanejamento( planejamentoDTO.getNomePlanejamento() );
        planejamentoEntity.setCargo( planejamentoDTO.getCargo() );
        planejamentoEntity.setAnoAplicacao( planejamentoDTO.getAnoAplicacao() );
        List<String> list = planejamentoDTO.getDisponibilidade();
        if ( list != null ) {
            planejamentoEntity.setDisponibilidade( new ArrayList<String>( list ) );
        }
        planejamentoEntity.setHorasDiarias( planejamentoDTO.getHorasDiarias() );
        List<MateriaPlanejamentoEntity> list1 = planejamentoDTO.getMaterias();
        if ( list1 != null ) {
            planejamentoEntity.setMaterias( new ArrayList<MateriaPlanejamentoEntity>( list1 ) );
        }
        List<SessaoEstudoEntity> list2 = planejamentoDTO.getSessoesEstudo();
        if ( list2 != null ) {
            planejamentoEntity.setSessoesEstudo( new ArrayList<SessaoEstudoEntity>( list2 ) );
        }
        planejamentoEntity.setCriador( planejamentoDTO.getCriador() );
        planejamentoEntity.setPlanoArquivado( planejamentoDTO.isPlanoArquivado() );
        planejamentoEntity.setPreDefinidoAdm( planejamentoDTO.isPreDefinidoAdm() );

        return planejamentoEntity;
    }

    @Override
    public PlanejamentoDTO toPlanejamentoDTO(PlanejamentoEntity planejamentoEntity) {
        if ( planejamentoEntity == null ) {
            return null;
        }

        PlanejamentoDTO planejamentoDTO = new PlanejamentoDTO();

        planejamentoDTO.setId( planejamentoEntity.getId() );
        planejamentoDTO.setNomePlanejamento( planejamentoEntity.getNomePlanejamento() );
        planejamentoDTO.setCargo( planejamentoEntity.getCargo() );
        planejamentoDTO.setAnoAplicacao( planejamentoEntity.getAnoAplicacao() );
        List<String> list = planejamentoEntity.getDisponibilidade();
        if ( list != null ) {
            planejamentoDTO.setDisponibilidade( new ArrayList<String>( list ) );
        }
        planejamentoDTO.setHorasDiarias( planejamentoEntity.getHorasDiarias() );
        planejamentoDTO.setMaterias( materiaPlanejamentoEntityListToMateriaPlanejamentoDTOList( planejamentoEntity.getMaterias() ) );
        planejamentoDTO.setSessoesEstudo( sessaoEstudoEntityListToSessaoEstudoDTOList( planejamentoEntity.getSessoesEstudo() ) );
        planejamentoDTO.setCriador( usuarioMapper.toUsuarioDTO( planejamentoEntity.getCriador() ) );
        planejamentoDTO.setPlanoArquivado( planejamentoEntity.isPlanoArquivado() );
        planejamentoDTO.setPreDefinidoAdm( planejamentoEntity.isPreDefinidoAdm() );

        return planejamentoDTO;
    }

    protected MateriaPlanejamentoDTO materiaPlanejamentoEntityToMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity) {
        if ( materiaPlanejamentoEntity == null ) {
            return null;
        }

        MateriaPlanejamentoDTO materiaPlanejamentoDTO = new MateriaPlanejamentoDTO();

        materiaPlanejamentoDTO.setId( materiaPlanejamentoEntity.getId() );
        materiaPlanejamentoDTO.setNivelConhecimento( materiaPlanejamentoEntity.getNivelConhecimento() );
        materiaPlanejamentoDTO.setCargaHorariaMateriaPlano( materiaPlanejamentoEntity.getCargaHorariaMateriaPlano() );

        return materiaPlanejamentoDTO;
    }

    protected List<MateriaPlanejamentoDTO> materiaPlanejamentoEntityListToMateriaPlanejamentoDTOList(List<MateriaPlanejamentoEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<MateriaPlanejamentoDTO> list1 = new ArrayList<MateriaPlanejamentoDTO>( list.size() );
        for ( MateriaPlanejamentoEntity materiaPlanejamentoEntity : list ) {
            list1.add( materiaPlanejamentoEntityToMateriaPlanejamentoDTO( materiaPlanejamentoEntity ) );
        }

        return list1;
    }

    protected SessaoEstudoDTO sessaoEstudoEntityToSessaoEstudoDTO(SessaoEstudoEntity sessaoEstudoEntity) {
        if ( sessaoEstudoEntity == null ) {
            return null;
        }

        SessaoEstudoDTO sessaoEstudoDTO = new SessaoEstudoDTO();

        sessaoEstudoDTO.setId( sessaoEstudoEntity.getId() );
        sessaoEstudoDTO.setDuracaoSessao( sessaoEstudoEntity.getDuracaoSessao() );

        return sessaoEstudoDTO;
    }

    protected List<SessaoEstudoDTO> sessaoEstudoEntityListToSessaoEstudoDTOList(List<SessaoEstudoEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SessaoEstudoDTO> list1 = new ArrayList<SessaoEstudoDTO>( list.size() );
        for ( SessaoEstudoEntity sessaoEstudoEntity : list ) {
            list1.add( sessaoEstudoEntityToSessaoEstudoDTO( sessaoEstudoEntity ) );
        }

        return list1;
    }
}
