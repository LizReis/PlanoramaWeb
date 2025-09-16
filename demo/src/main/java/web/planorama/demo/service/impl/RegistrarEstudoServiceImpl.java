package web.planorama.demo.service.impl;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.repository.RegistrarEstudoRepository;
import web.planorama.demo.entity.RegistrarEstudoEntity;
import web.planorama.demo.mapping.RegistrarEstudoMapper;;

@Service
@RequiredArgsConstructor
public class RegistrarEstudoImpl implements RegistrarEstudoService{

    private final RegistrarEstudoRepository registrarEstudoRepository;
    private final AssuntoRepository assuntoRepository;

    private final RegistrarEstudoMapper registrarEstudoMapper;

    public RegistrarEstudoDTO save(RegistrarEstudoDTO registrarEstudoDTO){
        AssuntoEntity assuntoEscolhido = assuntoRepository.findById(registrarEstudoDTO.getAssuntoId()).orElseThrow(() -> new 
        MyNotFoundException("Assunto não encontrado."));

        RegistrarEstudoEntity novoRegistro = new RegistroEstudoEntity();

        int horasRecebidas = registrarEstudoDTO.getHorasEstudadas();
        int minutosRecebidos = registrarEstudoDTO.getMinutosEstudados();
        //transforma as horas recebidas em minutos e soma com os minutos recebidos
        int tempoTotalEstudado = minutosRecebidos + (horasEstudadas * 60); 

        novoRegistro.setAssunto(assuntoEscolhido);
        novoRegistro.setDuracaoEmMinutos(tempoTotalEstudado);
        novoRegistro.setDataRegistro(LocalDateTime.now()); 
        
        registrarEstudoRepository.save(registrarEstudoMapper.toRegistrarDTO(novoRegistro));
    }

    public RegistrarEstudoDTO findById(UUID id){

    }

    public List<RegistrarEstudoDTO> findAll(){

    }

    public void remove(UUID id){

    }
}
