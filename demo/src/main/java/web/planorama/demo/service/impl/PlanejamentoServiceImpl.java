package web.planorama.demo.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.PlanejamentoService;

@Service
@RequiredArgsConstructor
public class PlanejamentoServiceImpl implements PlanejamentoService{

    private final PlanejamentoRepository planejamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanejamentoMapper mapper;

    @Override
    @Transactional
    public PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO) {
        validarPlanejamentoDTO(planejamentoDTO);

        UsuarioEntity criador = usuarioRepository.findById(planejamentoDTO.getCriador().getId())
                .orElseThrow(() -> new MyNotFoundException("Usuário com ID " + planejamentoDTO.getCriador().getId() + " não encontrado."));

        PlanejamentoEntity planejamentoEntity = mapper.toPlanejamentoEntity(planejamentoDTO);
        planejamentoEntity.setCriador(criador);
        return planejamentoDTO;

        //FALTA A LÓGICA PARA SALVAR A MATÉRIA E A DO TERCEIRO CARD QUE É RESPONSÁVEL POR
        //DEFINIR O NIVEL DE CONHECIMENTO DE CARGA HORÁRIA DE CADA MATÉRIA
        // ========LEMBRANDO QUE A LÓGICA DO NIVEL DE CONHECIMENTO E DA CARGA HORARIA=========
        //=========FICAM NO MateriaPlanejamento===============================================
        //DEPOIS DISSO TUDO, AÍ SIM O SAVE DO PLANEJAMENTO VAI SALVAR O PLANEJAMENTO
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
        if(planejamentoRepository.existsById(id)){
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
    public List<SessaoEstudoDTO> gerarCicloDeEstudos(UUID planejamentoId) {
        PlanejamentoEntity planejamento = planejamentoRepository.findById(planejamentoId)
                .orElseThrow(() -> new MyNotFoundException("Planejamento com ID " + planejamentoId + " não encontrado."));

        List<SessaoEstudoDTO> sessoes = new ArrayList<>();

        for (MateriaPlanejamentoEntity materiaPlanejada : planejamento.getMaterias()) {

            // Carga horária semanal da matéria em minutos
            int cargaHorariaTotalMinutos = materiaPlanejada.getCargaHorariaSemanal() * 60;

            // Duração de cada sessão de estudo para esta matéria
            int duracaoSessao = materiaPlanejada.getTempoSessao();

            if (duracaoSessao <= 0) {
                continue; // Pula matérias com tempo de sessão inválido
            }

            // Calcula quantas sessões cabem na carga horária semanal
            int numeroDeSessoes = cargaHorariaTotalMinutos / duracaoSessao;

            // Adiciona o número calculado de sessões à lista
            for (int i = 0; i < numeroDeSessoes; i++) {
                sessoes.add(new SessaoEstudoDTO(materiaPlanejada.getMateriaEntity().getNomeMateria(), duracaoSessao));
            }
        }

        // Embaralha a lista para que as matérias não fiquem sempre na mesma ordem
        Collections.shuffle(sessoes);

        return sessoes;
    }

    @Override
    @Transactional
    public PlanejamentoDTO restaurarPlanoDeEstudos(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restaurarPlanoDeEstudos'");
    }

    private void validarPlanejamentoDTO(PlanejamentoDTO dto) {
        if (dto.getNomePlanejamento() == null || dto.getNomePlanejamento().isBlank()) {
            throw new IllegalArgumentException("O nome do planejamento é obrigatório.");
        }
        if (dto.getCargo() == null || dto.getCargo().isBlank()) {
            throw new IllegalArgumentException("O cargo é obrigatório.");
        }
        if (dto.getAnoAplicacao() <= 2020) {
            throw new IllegalArgumentException("O ano de aplicação deve ser superior a 2020.");
        }
        if (dto.getCriador().getId() == null) {
            throw new IllegalArgumentException("O ID do criador do plano é obrigatório.");
        }
        if (dto.getDisponibilidade() == null) {
            throw new IllegalArgumentException("A disponibilidade de horários é obrigatória.");
        }
        if (dto.getMaterias() == null || dto.getMaterias().isEmpty()) {
            throw new IllegalArgumentException("O plano de estudos deve ter pelo menos uma matéria.");
        }
        dto.getMaterias().forEach(materia -> {
            if (materia.getMateriaEntity().getNomeMateria() == null || materia.getMateriaEntity().getNomeMateria().isBlank()) {
                throw new IllegalArgumentException("O nome da matéria não pode ser vazio.");
            }
            if (materia.getNivelConhecimento() < 1 || materia.getNivelConhecimento() > 5) {
                throw new IllegalArgumentException("A proficiência da matéria '" + materia.getMateriaEntity().getNomeMateria() + "' deve ser entre 1 e 5.");
            }
        });


    }

}
