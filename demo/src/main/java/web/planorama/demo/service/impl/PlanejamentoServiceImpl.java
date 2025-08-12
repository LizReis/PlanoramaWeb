package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.mapping.PlanejamentoMapper;
import web.planorama.demo.repository.PlanejamentoRepository;
import web.planorama.demo.service.PlanejamentoService;

@Service
@RequiredArgsConstructor
public class PlanejamentoServiceImpl implements PlanejamentoService{

    private final PlanejamentoRepository repository;
    private final PlanejamentoMapper mapper;

    @Override
    public PlanejamentoDTO save(PlanejamentoDTO planejamentoDTO) {
        var entity = repository.save(mapper.toPlanejamentoEntity(planejamentoDTO));
        return mapper.toPlanejamentoDTO(entity);
    }

    @Override
    public PlanejamentoDTO findOne(UUID id) {
       var entity = repository.findById(id).orElseThrow();
       return mapper.toPlanejamentoDTO(entity);
    }

    @Override
    public List<PlanejamentoDTO> findAll() {
       return repository.findAll()
                .stream()
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfAdm() {
        return repository.findAll()
                .stream()
                .filter(plano -> Boolean.TRUE.equals(plano.isPreDefinidoAdm()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public List<PlanejamentoDTO> findAllOfEstudante(EstudanteDTO estudanteDTO) {
       return repository.findAll()
                .stream()
                .filter(plano -> plano.getUsuario() != null && plano.getUsuario().getId().equals(estudanteDTO.id()))
                .map(mapper::toPlanejamentoDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

}
