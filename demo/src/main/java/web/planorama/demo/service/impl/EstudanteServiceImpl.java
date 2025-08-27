package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.EstudanteService;

@Service
@RequiredArgsConstructor
public class EstudanteServiceImpl implements EstudanteService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EstudanteDTO save(EstudanteDTO estudanteDTO) {
        if (repository.existsByEmail(estudanteDTO.email())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        } else {
            var entity = mapper.toEstudanteEntity(estudanteDTO);

            String senhaCriptograda = passwordEncoder.encode(estudanteDTO.senha());
            entity.setSenha(senhaCriptograda);

            var savedEntity = repository.save(entity);

            return mapper.toEstudanteDTO(savedEntity);
        }
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
