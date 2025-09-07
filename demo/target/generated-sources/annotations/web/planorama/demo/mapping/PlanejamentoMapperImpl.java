package web.planorama.demo.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-07T13:34:02-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Oracle Corporation)"
)
@Component
public class PlanejamentoMapperImpl implements PlanejamentoMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public PlanejamentoEntity toPlanejamentoEntity(PlanejamentoDTO planejamentoDTO) {
        if ( planejamentoDTO == null ) {
            return null;
        }

        PlanejamentoEntity planejamentoEntity = new PlanejamentoEntity();

        planejamentoEntity.setNomePlanejamento( planejamentoDTO.getNomePlanejamento() );
        planejamentoEntity.setCargo( planejamentoDTO.getCargo() );
        planejamentoEntity.setAnoAplicacao( planejamentoDTO.getAnoAplicacao() );
        List<String> list = planejamentoDTO.getDisponibilidade();
        if ( list != null ) {
            planejamentoEntity.setDisponibilidade( new ArrayList<String>( list ) );
        }
        planejamentoEntity.setHorasDiarias( planejamentoDTO.getHorasDiarias() );
        planejamentoEntity.setMaterias( materiaPlanejamentoDTOListToMateriaPlanejamentoEntityList( planejamentoDTO.getMaterias() ) );
        planejamentoEntity.setCriador( usuarioMapper.toUsuarioEntity( planejamentoDTO.getCriador() ) );
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

        planejamentoDTO.setNomePlanejamento( planejamentoEntity.getNomePlanejamento() );
        planejamentoDTO.setCargo( planejamentoEntity.getCargo() );
        planejamentoDTO.setAnoAplicacao( planejamentoEntity.getAnoAplicacao() );
        List<String> list = planejamentoEntity.getDisponibilidade();
        if ( list != null ) {
            planejamentoDTO.setDisponibilidade( new ArrayList<String>( list ) );
        }
        planejamentoDTO.setHorasDiarias( planejamentoEntity.getHorasDiarias() );
        planejamentoDTO.setMaterias( materiaPlanejamentoEntityListToMateriaPlanejamentoDTOList( planejamentoEntity.getMaterias() ) );
        planejamentoDTO.setCriador( usuarioMapper.toUsuarioDTO( planejamentoEntity.getCriador() ) );
        planejamentoDTO.setPlanoArquivado( planejamentoEntity.isPlanoArquivado() );
        planejamentoDTO.setPreDefinidoAdm( planejamentoEntity.isPreDefinidoAdm() );

        return planejamentoDTO;
    }

    protected MateriaPlanejamentoEntity materiaPlanejamentoDTOToMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO) {
        if ( materiaPlanejamentoDTO == null ) {
            return null;
        }

        MateriaPlanejamentoEntity materiaPlanejamentoEntity = new MateriaPlanejamentoEntity();

        materiaPlanejamentoEntity.setId( materiaPlanejamentoDTO.getId() );
        if ( materiaPlanejamentoDTO.getNivelConhecimento() != null ) {
            materiaPlanejamentoEntity.setNivelConhecimento( materiaPlanejamentoDTO.getNivelConhecimento() );
        }
        if ( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() != null ) {
            materiaPlanejamentoEntity.setCargaHorariaMateriaPlano( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() );
        }

        return materiaPlanejamentoEntity;
    }

    protected List<MateriaPlanejamentoEntity> materiaPlanejamentoDTOListToMateriaPlanejamentoEntityList(List<MateriaPlanejamentoDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<MateriaPlanejamentoEntity> list1 = new ArrayList<MateriaPlanejamentoEntity>( list.size() );
        for ( MateriaPlanejamentoDTO materiaPlanejamentoDTO : list ) {
            list1.add( materiaPlanejamentoDTOToMateriaPlanejamentoEntity( materiaPlanejamentoDTO ) );
        }

        return list1;
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
}
