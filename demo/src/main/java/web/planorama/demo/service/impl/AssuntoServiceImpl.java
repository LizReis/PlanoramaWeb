package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.AssuntoDTO;
import web.planorama.demo.mapping.AssuntoMapper;
import web.planorama.demo.repository.AssuntoRepository;
import web.planorama.demo.service.AssuntoService;

@Service
@RequiredArgsConstructor
public class AssuntoServiceImpl implements AssuntoService{

    private final AssuntoRepository assuntoRepository;
    private final AssuntoMapper assuntoMapper;

    @Override
    public AssuntoDTO save(AssuntoDTO assuntoDTO) {
        var entity = assuntoRepository.save(assuntoMapper.toAssuntoEntity(assuntoDTO));
        return assuntoMapper.toAssuntoDTO(entity);
    }

    @Override
    public AssuntoDTO findById(UUID id) {
        var entity = assuntoRepository.findById(id).orElseThrow();
       return assuntoMapper.toAssuntoDTO(entity);
    }

    @Override
    public List<AssuntoDTO> findAll() {
        return assuntoRepository.findAll()
                .stream()
                .map(assuntoMapper::toAssuntoDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if(assuntoRepository.existsById(id)){
            assuntoRepository.deleteById(id);
        }
    }

}
