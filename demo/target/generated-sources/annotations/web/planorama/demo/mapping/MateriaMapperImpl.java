package web.planorama.demo.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.SessaoEstudoEntity;
import web.planorama.demo.entity.UsuarioEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-13T20:20:27-0300",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class MateriaMapperImpl implements MateriaMapper {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public MateriaEntity toMateriaEntity(MateriaDTO materiaDTO) {
        if ( materiaDTO == null ) {
            return null;
        }

        MateriaEntity materiaEntity = new MateriaEntity();

        materiaEntity.setCriadoPor( usuarioDTOToUsuarioEntity( materiaDTO.getCriadoPor() ) );
        materiaEntity.setId( materiaDTO.getId() );
        materiaEntity.setListaAssuntos( assuntoDTOListToAssuntoEntityList( materiaDTO.getListaAssuntos() ) );
        materiaEntity.setListaSessao( sessaoEstudoDTOListToSessaoEstudoEntityList( materiaDTO.getListaSessao() ) );
        materiaEntity.setNomeMateria( materiaDTO.getNomeMateria() );
        materiaEntity.setPlanejamentosComMateria( materiaPlanejamentoDTOListToMateriaPlanejamentoEntityList( materiaDTO.getPlanejamentosComMateria() ) );

        return materiaEntity;
    }

    @Override
    public MateriaDTO toMateriaDTO(MateriaEntity materiaEntity) {
        if ( materiaEntity == null ) {
            return null;
        }

        MateriaDTO materiaDTO = new MateriaDTO();

        materiaDTO.setCriadoPor( usuarioMapper.toUsuarioDTO( materiaEntity.getCriadoPor() ) );
        materiaDTO.setId( materiaEntity.getId() );
        materiaDTO.setListaAssuntos( assuntoEntityListToAssuntoDTOList( materiaEntity.getListaAssuntos() ) );
        materiaDTO.setListaSessao( sessaoEstudoEntityListToSessaoEstudoDTOList( materiaEntity.getListaSessao() ) );
        materiaDTO.setNomeMateria( materiaEntity.getNomeMateria() );
        materiaDTO.setPlanejamentosComMateria( materiaPlanejamentoEntityListToMateriaPlanejamentoDTOList( materiaEntity.getPlanejamentosComMateria() ) );

        return materiaDTO;
    }

    protected UsuarioEntity usuarioDTOToUsuarioEntity(UsuarioDTO usuarioDTO) {
        if ( usuarioDTO == null ) {
            return null;
        }

        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setDescricaoUsuario( usuarioDTO.descricaoUsuario() );
        usuarioEntity.setEmail( usuarioDTO.email() );
        usuarioEntity.setFotoUsuario( usuarioDTO.fotoUsuario() );
        usuarioEntity.setId( usuarioDTO.id() );
        usuarioEntity.setNome( usuarioDTO.nome() );
        usuarioEntity.setSenha( usuarioDTO.senha() );

        return usuarioEntity;
    }

    protected AssuntoEntity assuntoDTOToAssuntoEntity(AssuntoDTO assuntoDTO) {
        if ( assuntoDTO == null ) {
            return null;
        }

        AssuntoEntity assuntoEntity = new AssuntoEntity();

        assuntoEntity.setId( assuntoDTO.getId() );
        assuntoEntity.setNomeAssunto( assuntoDTO.getNomeAssunto() );

        return assuntoEntity;
    }

    protected List<AssuntoEntity> assuntoDTOListToAssuntoEntityList(List<AssuntoDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<AssuntoEntity> list1 = new ArrayList<AssuntoEntity>( list.size() );
        for ( AssuntoDTO assuntoDTO : list ) {
            list1.add( assuntoDTOToAssuntoEntity( assuntoDTO ) );
        }

        return list1;
    }

    protected SessaoEstudoEntity sessaoEstudoDTOToSessaoEstudoEntity(SessaoEstudoDTO sessaoEstudoDTO) {
        if ( sessaoEstudoDTO == null ) {
            return null;
        }

        SessaoEstudoEntity sessaoEstudoEntity = new SessaoEstudoEntity();

        sessaoEstudoEntity.setDuracaoSessao( sessaoEstudoDTO.getDuracaoSessao() );
        sessaoEstudoEntity.setId( sessaoEstudoDTO.getId() );

        return sessaoEstudoEntity;
    }

    protected List<SessaoEstudoEntity> sessaoEstudoDTOListToSessaoEstudoEntityList(List<SessaoEstudoDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SessaoEstudoEntity> list1 = new ArrayList<SessaoEstudoEntity>( list.size() );
        for ( SessaoEstudoDTO sessaoEstudoDTO : list ) {
            list1.add( sessaoEstudoDTOToSessaoEstudoEntity( sessaoEstudoDTO ) );
        }

        return list1;
    }

    protected MateriaPlanejamentoEntity materiaPlanejamentoDTOToMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO) {
        if ( materiaPlanejamentoDTO == null ) {
            return null;
        }

        MateriaPlanejamentoEntity materiaPlanejamentoEntity = new MateriaPlanejamentoEntity();

        if ( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() != null ) {
            materiaPlanejamentoEntity.setCargaHorariaMateriaPlano( materiaPlanejamentoDTO.getCargaHorariaMateriaPlano() );
        }
        materiaPlanejamentoEntity.setId( materiaPlanejamentoDTO.getId() );
        if ( materiaPlanejamentoDTO.getNivelConhecimento() != null ) {
            materiaPlanejamentoEntity.setNivelConhecimento( materiaPlanejamentoDTO.getNivelConhecimento() );
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

    protected AssuntoDTO assuntoEntityToAssuntoDTO(AssuntoEntity assuntoEntity) {
        if ( assuntoEntity == null ) {
            return null;
        }

        AssuntoDTO assuntoDTO = new AssuntoDTO();

        assuntoDTO.setId( assuntoEntity.getId() );
        assuntoDTO.setNomeAssunto( assuntoEntity.getNomeAssunto() );

        return assuntoDTO;
    }

    protected List<AssuntoDTO> assuntoEntityListToAssuntoDTOList(List<AssuntoEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<AssuntoDTO> list1 = new ArrayList<AssuntoDTO>( list.size() );
        for ( AssuntoEntity assuntoEntity : list ) {
            list1.add( assuntoEntityToAssuntoDTO( assuntoEntity ) );
        }

        return list1;
    }

    protected SessaoEstudoDTO sessaoEstudoEntityToSessaoEstudoDTO(SessaoEstudoEntity sessaoEstudoEntity) {
        if ( sessaoEstudoEntity == null ) {
            return null;
        }

        SessaoEstudoDTO sessaoEstudoDTO = new SessaoEstudoDTO();

        sessaoEstudoDTO.setDuracaoSessao( sessaoEstudoEntity.getDuracaoSessao() );
        sessaoEstudoDTO.setId( sessaoEstudoEntity.getId() );

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

    protected MateriaPlanejamentoDTO materiaPlanejamentoEntityToMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity) {
        if ( materiaPlanejamentoEntity == null ) {
            return null;
        }

        MateriaPlanejamentoDTO materiaPlanejamentoDTO = new MateriaPlanejamentoDTO();

        materiaPlanejamentoDTO.setCargaHorariaMateriaPlano( materiaPlanejamentoEntity.getCargaHorariaMateriaPlano() );
        materiaPlanejamentoDTO.setId( materiaPlanejamentoEntity.getId() );
        materiaPlanejamentoDTO.setNivelConhecimento( materiaPlanejamentoEntity.getNivelConhecimento() );

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
