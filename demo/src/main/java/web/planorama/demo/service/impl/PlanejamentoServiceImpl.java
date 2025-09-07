package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;

@Service
@RequiredArgsConstructor
public class PlanejamentoServiceImpl implements PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final PlanejamentoMapper mapper;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;

    @Override
    @Transactional
    public PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailUsuarioLogado = ((UserDetails) principal).getUsername();
        UsuarioEntity criador = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        PlanejamentoEntity planejamentoEntity = mapper.toPlanejamentoEntity(planejamentoDTO);
        planejamentoEntity.setCriador(criador);
        planejamentoEntity.setNomePlanejamento(planejamentoDTO.getNomePlanejamento());
        planejamentoEntity.setDisponibilidade(planejamentoDTO.getDisponibilidade());
        planejamentoEntity.setCargo(planejamentoDTO.getCargo());
        planejamentoEntity.setAnoAplicacao(planejamentoDTO.getAnoAplicacao());
        planejamentoEntity.setHorasDiarias(planejamentoDTO.getHorasDiarias());
        planejamentoEntity.setPlanoArquivado(planejamentoDTO.isPlanoArquivado());
        planejamentoEntity.setPreDefinidoAdm(planejamentoDTO.isPreDefinidoAdm());
        //if(criador.){
        //    planejamentoEntity.setPreDefinidoAdm(false);
        //}else{
        //    planejamentoEntity.setPreDefinidoAdm(true);
        //}
        
        if(planejamentoEntity.getMaterias() != null){
            List<MateriaPlanejamentoEntity> materiasDoPlanejamento = planejamentoDTO.getMaterias().stream().map(materiaPlano -> {
                MateriaPlanejamentoEntity materiaPlanejamento =  materiaPlanejamentoMapper.toMateriaPlanejamentoEntity(materiaPlano);
                materiaPlanejamento.setNivelConhecimento(materiaPlano.getNivelConhecimento());
                materiaPlanejamento.setCargaHorariaMateriaPlano(materiaPlano.getCargaHorariaMateriaPlano());
                materiaPlanejamento.setPlanejamentoEntity(planejamentoEntity);

                MateriaEntity materiaEntity = materiaRepository.findById(materiaPlano.getIdMateriaDTO()).orElseThrow(() -> new RuntimeException("Matéria não encontrada."));

                materiaPlanejamento.setMateriaEntity(materiaEntity);
                materiaPlanejamento.setPlanejamentoEntity(planejamentoEntity);

                return materiaPlanejamento;
            }).collect(Collectors.toList());

            planejamentoEntity.setMaterias(materiasDoPlanejamento);
        }

        return mapper.toPlanejamentoDTO(planejamentoRepository.save(planejamentoEntity));

    }

    @Override
    public PlanejamentoDTO findOne(UUID id) {
        var entity = planejamentoRepository.findById(id).orElseThrow();
        return mapper.toPlanejamentoDTO(entity);
    }

    @Override
    public List<PlanejamentoDTO> findAll() {
        return planejamentoRepository.findAll()
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfAdm() {
        return planejamentoRepository.findAll()
                .stream()
                .filter(plano -> Boolean.TRUE.equals(plano.isPreDefinidoAdm()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfEstudante(EstudanteDTO estudanteDTO) {
        return planejamentoRepository.findAll()
                .stream()
                .filter(plano -> plano.getCriador() != null && plano.getCriador().getId().equals(estudanteDTO.id()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if (planejamentoRepository.existsById(id)) {
            planejamentoRepository.deleteById(id);
        }
    }

    @Override
    public PlanejamentoDTO atualizarPlanoDeEstudos(UUID id, PlanejamentoDTO planejamentoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizarPlanoDeEstudos'");
    }

    @Override
    @Transactional
    public PlanejamentoDTO arquivarPlanoDeEstudos(UUID id) {
        PlanejamentoEntity entity = planejamentoRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + id + " não encontrado."));
        entity.setPlanoArquivado(true);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    @Override
    @Transactional
    public PlanejamentoDTO desArquivarPlanoDeEstudos(UUID id) {
        PlanejamentoEntity entity = planejamentoRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + id + " não encontrado."));
        entity.setPlanoArquivado(false);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    @Override
    @Transactional
    public PlanejamentoDTO restaurarPlanoDeEstudos(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restaurarPlanoDeEstudos'");
    }


    @Override
    public List<SessaoEstudoDTO> gerarCicloDeEstudos(UUID planejamentoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gerarCicloDeEstudos'");
    }

}
