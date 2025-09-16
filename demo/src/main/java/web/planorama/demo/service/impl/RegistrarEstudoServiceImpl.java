package web.planorama.demo.service.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.repository.RegistrarEstudoRepository;
import web.planorama.demo.entity.RegistrarEstudoEntity;
import web.planorama.demo.mapping.RegistrarEstudoMapper;
import web.planorama.demo.service.RegistrarEstudoService;;

@Service
@RequiredArgsConstructor

public class RegistrarEstudoServiceImpl implements RegistrarEstudoService {

    private final RegistrarEstudoRepository RegistrarEstudoRepository;
    private final AssuntoRepository assuntoRepository;

    private final RegistrarEstudoMapper registrarEstudoMapper;

    public RegistrarEstudoDTO save(RegistrarEstudoDTO registrarEstudoDTO){
        AssuntoEntity assuntoEscolhido = assuntoRepository.findById(RegistrarEstudoDTO.getAssuntoId()).orElseThrow(() -> new
                MyNotFoundException("Assunto não encontrado."));

        RegistrarEstudoEntity novoRegistro = new RegistrarEstudoEntity();

        int horasRecebidas = registrarEstudoDTO.getHorasEstudadas();
        int minutosRecebidos = registrarEstudoDTO.getMinutosEstudados();
        //transforma as horas recebidas em minutos e soma com os minutos recebidos
        int tempoTotalEstudado = minutosRecebidos + horasRecebidas * 60;

        novoRegistro.setAssunto(assuntoEscolhido);
        novoRegistro.setDuracaoEmMinutos(tempoTotalEstudado);
        novoRegistro.setDataRegistro(LocalDateTime.now());

        RegistrarEstudoRepository.save(registrarEstudoMapper.toRegistrarDTO(novoRegistro));
    }

    public RegistrarEstudoDTO findById(UUID id){

    }

    public List<RegistrarEstudoDTO> findAll(){

    }

    public void remove(UUID id){

    }
}
