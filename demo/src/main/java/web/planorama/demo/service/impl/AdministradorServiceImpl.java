package web.planorama.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.AdministradorRepository;
import web.planorama.demo.service.AdministradorService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdministradorServiceImpl implements AdministradorService {

    private final AdministradorRepository repository;
    private final UsuarioMapper mapper;


    @Override
    public AdministradorDTO save(AdministradorDTO administradorDTO) {
        var entity = repository.save(mapper.toAdministradorEntity(administradorDTO));
        return mapper.toAdministradorDTO(entity);
    }

    @Override
    public AdministradorDTO findOne(UUID id) {
        var entity = repository.findById(id).orElseThrow();
        return mapper.toAdministradorDTO((AdministradorEntity) entity);
    }

    @Override
    public List<AdministradorDTO> findAll() {
        return repository.findAll()
                .stream()
                .filter(AdministradorEntity.class::isInstance)
                .map(AdministradorEntity.class::cast)
                .map(mapper::toAdministradorDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
