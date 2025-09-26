package web.planorama.demo.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.DesempenhoDTO;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.dto.SessaoEstudoDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.repository.MateriaPlanejamentoRepository;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.RegistrarEstudoRepository;
import web.planorama.demo.repository.SessaoEstudoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.entity.RegistrarEstudoEntity;
import web.planorama.demo.entity.SessaoEstudoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.RegistrarEstudoMapper;
import web.planorama.demo.service.PlanejamentoService;
import web.planorama.demo.service.RegistrarEstudoService;

@Service
@RequiredArgsConstructor

public class RegistrarEstudoServiceImpl implements RegistrarEstudoService {

    private final RegistrarEstudoRepository registrarEstudoRepository;
    private final AssuntoRepository assuntoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final PlanejamentoRepository planejamentoRepository;
    private final SessaoEstudoRepository sessaoEstudoRepository;

    private final MateriaPlanejamentoRepository materiaPlanejamentoRepository;

    private final RegistrarEstudoMapper registrarEstudoMapper;

    private final PlanejamentoService planejamentoService;

    @Override
    @Transactional
    public RegistrarEstudoDTO save(RegistrarEstudoDTO registrarEstudoDTO) {
        
        MateriaPlanejamentoEntity materiaPlanejamento = materiaPlanejamentoRepository
                .findById(registrarEstudoDTO.getIdMateriaPlanejamento())
                .orElseThrow(() -> new MyNotFoundException("O vínculo da matéria com o planejamento não foi encontrado."));

        AssuntoEntity assuntoEscolhido = assuntoRepository.findById(registrarEstudoDTO.getAssuntoId())
                .orElseThrow(() -> new MyNotFoundException("Assunto não encontrado."));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String emailLogado = ((UserDetails) principal).getUsername();
        UsuarioEntity usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        int tempoTotalEstudado = registrarEstudoDTO.getMinutosEstudados() + registrarEstudoDTO.getHorasEstudadas() * 60;
        RegistrarEstudoEntity novoRegistro = new RegistrarEstudoEntity();
        novoRegistro.setAssunto(assuntoEscolhido);
        novoRegistro.setMateriaPlanejamento(materiaPlanejamento);
        novoRegistro.setDuracaoEmMinutos(tempoTotalEstudado);
        novoRegistro.setDataRegistro(LocalDateTime.now());
        novoRegistro.setUsuario(usuarioLogado);
        
        var estudoSalvo = registrarEstudoRepository.save(novoRegistro);

        final int DURACAO_BLOCO_MINUTOS = 50;
        int sessoesParaRemover = tempoTotalEstudado / DURACAO_BLOCO_MINUTOS;

        if (sessoesParaRemover > 0) {
            List<SessaoEstudoEntity> sessoesRestantes = sessaoEstudoRepository.findByMateriaPlanejamento(materiaPlanejamento);

            if (sessoesRestantes != null && !sessoesRestantes.isEmpty()) {
                int numeroRealARemover = Math.min(sessoesParaRemover, sessoesRestantes.size());
                
                List<SessaoEstudoEntity> sessoesARemover = sessoesRestantes.subList(0, numeroRealARemover);
                
                sessaoEstudoRepository.deleteAllInBatch(sessoesARemover);
            }
        }

        return registrarEstudoMapper.toRegistrarEstudoDTO(estudoSalvo);
    }

    @Override
    public List<RegistrarEstudoDTO> findAll() {
        return registrarEstudoRepository.findAll()
                .stream()
                .map(registrarEstudoMapper::toRegistrarEstudoDTO)
                .toList();
    }

    @Override
    public RegistrarEstudoDTO findById(UUID id) {
        var entity = registrarEstudoRepository.findById(id).orElseThrow();
        return registrarEstudoMapper.toRegistrarEstudoDTO(entity);
    }

    @Override
    public void remove(UUID id) {
        if(registrarEstudoRepository.existsById(id)){
            registrarEstudoRepository.deleteById(id);
        }
    }


    @Override
    public List<DesempenhoDTO> getDesempenhoPorMateria(UUID usuarioId) {
        return registrarEstudoRepository.getDesempenhoPorMateria(usuarioId);
    }

    @Override
    @Transactional
    public void resetarEstudosDoPlano(UUID planejamentoId) {
        registrarEstudoRepository.deleteByPlanejamentoId(planejamentoId);

        planejamentoService.refazerPlanejamento(planejamentoId);
    }
}

