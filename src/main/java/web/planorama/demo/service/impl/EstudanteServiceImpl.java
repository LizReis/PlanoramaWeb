package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.EstudanteRepository;
import web.planorama.demo.service.EstudanteService;

@Service
@RequiredArgsConstructor
public class EstudanteServiceImpl implements EstudanteService{

    private final EstudanteRepository repository;
    private final UsuarioMapper mapper;


    @Override
    public EstudanteDTO save(EstudanteDTO estudanteDTO) {
        var entity = repository.save(mapper.toEstudanteEntity(estudanteDTO));
        return mapper.toEstudanteDTO(entity);
    }

    @Override
    public EstudanteDTO findOne(UUID id) {
        var entity = repository.findById(id).orElseThrow();
        return mapper.toEstudanteDTO((EstudanteEntity) entity);
    }

    @Override
    public List<EstudanteDTO> findAll() {
        return repository.findAll()
                .stream()
                .filter(EstudanteEntity.class::isInstance)
                .map(EstudanteEntity.class::cast)
                .map(mapper::toEstudanteDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
