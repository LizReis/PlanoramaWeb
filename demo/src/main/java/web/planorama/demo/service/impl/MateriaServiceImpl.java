package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.mapping.MateriaMapper;
import web.planorama.demo.repository.MateriaRepository;
import web.planorama.demo.service.MateriaService;

@Service
@RequiredArgsConstructor
public class MateriaServiceImpl implements MateriaService{

    private final MateriaRepository repository;
    private final MateriaMapper mapper;
    
    @Override
    public MateriaDTO save(MateriaDTO materiaDTO) {
        var entity = repository.save(mapper.toMateriaEntity(materiaDTO));
        return mapper.toMateriaDTO(entity);
    }

    @Override
    public MateriaDTO findOne(UUID id) {
       var entity = repository.findById(id).orElseThrow();
       return mapper.toMateriaDTO(entity);
    }

    @Override
    public List<MateriaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toMateriaDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

}
