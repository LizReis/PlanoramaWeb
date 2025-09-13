package web.planorama.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.dto.UsuarioDTO;
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

import static web.planorama.demo.enums.PapeisUsuario.*;

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

        UsuarioEntity criador = pegaUsuarioLogado();

        PlanejamentoEntity planejamentoEntity = mapper.toPlanejamentoEntity(planejamentoDTO);
        planejamentoEntity.setCriador(criador);
        planejamentoEntity.setNomePlanejamento(planejamentoDTO.getNomePlanejamento());
        planejamentoEntity.setDisponibilidade(planejamentoDTO.getDisponibilidade());
        planejamentoEntity.setCargo(planejamentoDTO.getCargo());
        planejamentoEntity.setAnoAplicacao(planejamentoDTO.getAnoAplicacao());
        planejamentoEntity.setHorasDiarias(planejamentoDTO.getHorasDiarias());
        planejamentoEntity.setPlanoArquivado(planejamentoDTO.isPlanoArquivado());

        boolean isAdmin = criador.getPapeis().stream().anyMatch(papel -> papel.getNome().equals(ADMIN.name()));
        planejamentoEntity.setPreDefinidoAdm(isAdmin);
        
        
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

        var planoSalvo = planejamentoRepository.save(planejamentoEntity);

        return mapper.toPlanejamentoDTO(planoSalvo);

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
    public List<PlanejamentoDTO> findAllOfEstudante(UsuarioDTO usuarioDTO) {
        return planejamentoRepository.findAll()
                .stream()
                .filter(plano -> plano.getCriador() != null && plano.getCriador().getId().equals(usuarioDTO.id()))
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

    @Override
    public PlanejamentoDTO selecionarPlanoPredefinido(UUID idPlanejamento) {
        UsuarioEntity usuarioLogado = pegaUsuarioLogado();

        PlanejamentoEntity planejamentoOriginal = planejamentoRepository.findById(idPlanejamento).orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado para seleção."));

        PlanejamentoEntity planejamentoCopia = new PlanejamentoEntity();

        planejamentoCopia.setNomePlanejamento(planejamentoOriginal.getNomePlanejamento());
        planejamentoCopia.setDisponibilidade(new ArrayList<>(planejamentoOriginal.getDisponibilidade()));
        planejamentoCopia.setCargo(planejamentoOriginal.getCargo());
        planejamentoCopia.setAnoAplicacao(planejamentoOriginal.getAnoAplicacao());
        planejamentoCopia.setHorasDiarias(planejamentoOriginal.getHorasDiarias());
        planejamentoCopia.setPlanoArquivado(planejamentoOriginal.isPlanoArquivado());

        planejamentoCopia.setCriador(usuarioLogado);
        planejamentoCopia.setPlanoArquivado(false);
        planejamentoCopia.setPreDefinidoAdm(false);

        if(planejamentoOriginal.getMaterias() != null){
            List<MateriaPlanejamentoEntity> materiasCopiadas = planejamentoOriginal.getMaterias().stream().map(materiaOriginal -> {
                MateriaPlanejamentoEntity materiaCopia = new MateriaPlanejamentoEntity();

                materiaCopia.setMateriaEntity(materiaOriginal.getMateriaEntity());
                materiaCopia.setPlanejamentoEntity(planejamentoCopia);
                materiaCopia.setNivelConhecimento(materiaOriginal.getNivelConhecimento());
                materiaCopia.setCargaHorariaMateriaPlano(materiaOriginal.getCargaHorariaMateriaPlano());

                return materiaCopia;
            }).collect(Collectors.toList());

            planejamentoCopia.setMaterias(materiasCopiadas);
        }

        return mapper.toPlanejamentoDTO(planejamentoRepository.save(planejamentoCopia));
    }

    public UsuarioEntity pegaUsuarioLogado(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String emailLogado = ((UserDetails) principal).getUsername();

        UsuarioEntity usuarioLogado = usuarioRepository.findByEmail(emailLogado).orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        return usuarioLogado;
    }
}
