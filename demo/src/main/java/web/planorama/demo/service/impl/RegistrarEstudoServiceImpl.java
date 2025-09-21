package web.planorama.demo.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.repository.RegistrarEstudoRepository;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.entity.RegistrarEstudoEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.mapping.RegistrarEstudoMapper;
import web.planorama.demo.service.RegistrarEstudoService;

@Service
@RequiredArgsConstructor

public class RegistrarEstudoServiceImpl implements RegistrarEstudoService {

    private final RegistrarEstudoRepository registrarEstudoRepository;
    private final AssuntoRepository assuntoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MateriaRepository materiaRepository;
    private final PlanejamentoRepository planejamentoRepository;

    private final RegistrarEstudoMapper registrarEstudoMapper;

    @Override
    public RegistrarEstudoDTO save(RegistrarEstudoDTO registrarEstudoDTO){
        AssuntoEntity assuntoEscolhido = assuntoRepository.findById(registrarEstudoDTO.getAssuntoId()).orElseThrow(() -> new
                MyNotFoundException("Assunto não encontrado."));

        MateriaEntity materia = materiaRepository.findById(registrarEstudoDTO.getMateriaId())
                .orElseThrow(() -> new MyNotFoundException("Matéria não encontrada."));

        PlanejamentoEntity planejamento = planejamentoRepository.findById(registrarEstudoDTO.getPlanejamentoId())
                .orElseThrow(() -> new MyNotFoundException("Planejamento não encontrado."));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String emailLogado = ((UserDetails) principal).getUsername();

        UsuarioEntity usuarioLogado = usuarioRepository.findByEmail(emailLogado)
                .orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));

        RegistrarEstudoEntity novoRegistro = new RegistrarEstudoEntity();

        int tempoTotalEstudado = registrarEstudoDTO.getMinutosEstudados() 
                               + registrarEstudoDTO.getHorasEstudadas() * 60;

        novoRegistro.setAssunto(assuntoEscolhido);
        novoRegistro.setMateria(materia); 
        novoRegistro.setPlanejamento(planejamento);
        novoRegistro.setDuracaoEmMinutos(tempoTotalEstudado);
        novoRegistro.setDataRegistro(LocalDateTime.now());
        novoRegistro.setUsuario(usuarioLogado);


        var estudoSalvo =  registrarEstudoRepository.save(novoRegistro);
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
}
