package web.planorama.demo.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.DisponibilidadeEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlanejamentoServiceImpl implements PlanejamentoService {

    private final PlanejamentoRepository planejamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanejamentoMapper mapper;

    // Construtor explícito para injeção de dependências
    public PlanejamentoServiceImpl(
            PlanejamentoRepository planejamentoRepository,
            UsuarioRepository usuarioRepository,
            PlanejamentoMapper mapper
    ) {
        this.planejamentoRepository = planejamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public PlanejamentoDTO save(PlanejamentoDTO dto) {
        validarPlanejamentoDTO(dto);

        UsuarioEntity criador = usuarioRepository.findById(dto.criadorId())
                .orElseThrow(() -> new MyNotFoundException("Usuário com ID " + dto.criadorId() + " não encontrado."));

        PlanejamentoEntity planejamentoEntity = mapper.toPlanejamentoEntity(dto);
        planejamentoEntity.setCriador(criador);

        DisponibilidadeEntity disponibilidadeEntity = mapper.disponibilidadeDTOToEntity(dto.disponibilidade());
        disponibilidadeEntity.setPlanejamento(planejamentoEntity);
        planejamentoEntity.setDisponibilidade(disponibilidadeEntity);

        List<MateriaEntity> materiaEntities = new ArrayList<>();
        if (dto.materias() != null) {
            dto.materias().forEach(materiaDTO -> {
                MateriaEntity materiaEntity = mapper.materiaToEntity(materiaDTO);
                materiaEntity.setPlanejamento(planejamentoEntity);
                materiaEntities.add(materiaEntity);
            });
        }
        planejamentoEntity.setMaterias(materiaEntities);

        PlanejamentoEntity savedPlanejamento = planejamentoRepository.save(planejamentoEntity);
        return mapper.toPlanejamentoDTO(savedPlanejamento);
    }

    @Override
    public PlanejamentoDTO findOne(UUID id) {
        var entity = planejamentoRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + id + " não encontrado."));
        return mapper.toPlanejamentoDTO(entity);
    }

    @Override
    public List<PlanejamentoDTO> findAll() {
        return planejamentoRepository.findAll()
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanejamentoDTO> findAllOfAdm() {
        return planejamentoRepository.findByPreDefinidoAdmIsTrue()
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanejamentoDTO> findAllOfEstudante(EstudanteDTO estudanteDTO) {
        return List.of();
    }

    @Override
    public PlanejamentoDTO atualizarPlanoDeEstudos(UUID id, PlanejamentoDTO planejamentoDTO) {
        return null;
    }

    @Override
    public PlanejamentoDTO arquivarPlanoDeEstudos(UUID id) {
        return null;
    }

    @Override
    public PlanejamentoDTO restaurarPlanoDeEstudos(UUID id) {
        return null;
    }

    @Override
    public List<PlanejamentoDTO> findAllOfEstudante(UUID estudanteId) {
        return planejamentoRepository.findByCriador_Id(estudanteId)
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(UUID id) {
        if(!planejamentoRepository.existsById(id)){
            throw new MyNotFoundException("Não é possível remover. Planejamento com ID " + id + " não encontrado.");
        }
        planejamentoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PlanejamentoDTO archive(UUID id) {
        PlanejamentoEntity entity = planejamentoRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + id + " não encontrado."));
        entity.setPlanoArquivado(true);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    @Override
    @Transactional
    public PlanejamentoDTO unarchive(UUID id) {
        PlanejamentoEntity entity = planejamentoRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + id + " não encontrado."));
        entity.setPlanoArquivado(false);
        PlanejamentoEntity updatedEntity = planejamentoRepository.save(entity);
        return mapper.toPlanejamentoDTO(updatedEntity);
    }

    private void validarPlanejamentoDTO(PlanejamentoDTO dto) {
        if (dto.nomePlanejamento() == null || dto.nomePlanejamento().isBlank()) {
            throw new IllegalArgumentException("O nome do planejamento é obrigatório.");
        }
        if (dto.cargo() == null || dto.cargo().isBlank()) {
            throw new IllegalArgumentException("O cargo é obrigatório.");
        }
        if (dto.anoAplicacao() <= 2020) {
            throw new IllegalArgumentException("O ano de aplicação deve ser superior a 2020.");
        }
        if (dto.criadorId() == null) {
            throw new IllegalArgumentException("O ID do criador do plano é obrigatório.");
        }
        if (dto.disponibilidade() == null) {
            throw new IllegalArgumentException("A disponibilidade de horários é obrigatória.");
        }
        if (dto.materias() == null || dto.materias().isEmpty()) {
            throw new IllegalArgumentException("O plano de estudos deve ter pelo menos uma matéria.");
        }
        dto.materias().forEach(materia -> {
            if (materia.nomeMateria() == null || materia.nomeMateria().isBlank()) {
                throw new IllegalArgumentException("O nome da matéria não pode ser vazio.");
            }
            if (materia.proficiencia() < 1 || materia.proficiencia() > 5) {
                throw new IllegalArgumentException("A proficiência da matéria '" + materia.nomeMateria() + "' deve ser entre 1 e 5.");
            }
        });
    }
}