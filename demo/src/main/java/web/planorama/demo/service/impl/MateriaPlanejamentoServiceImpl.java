package web.planorama.demo.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.mapping.MateriaPlanejamentoMapper;
import web.planorama.demo.repository.MateriaPlanejamentoRepository;
import web.planorama.demo.service.MateriaPlanejamentoService;

@Service
@RequiredArgsConstructor
public class MateriaPlanejamentoServiceImpl implements MateriaPlanejamentoService{

    private final MateriaPlanejamentoRepository materiaPlanejamentoRepository;
    private final MateriaPlanejamentoMapper materiaPlanejamentoMapper;

    @Override
    public MateriaPlanejamentoDTO save(MateriaPlanejamentoDTO materiaPlanejamentoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public MateriaPlanejamentoDTO findById(UUID id) {
        var entity = materiaPlanejamentoRepository.findById(id).orElseThrow();
       return materiaPlanejamentoMapper.toMateriaPlanejamentoDTO(entity);
    }

    @Override
    public List<MateriaPlanejamentoDTO> findAll() {
        return materiaPlanejamentoRepository.findAll()
                .stream()
                .map(materiaPlanejamentoMapper::toMateriaPlanejamentoDTO)
                .toList();
    }

    @Override
    public void remove(UUID id) {
        if(materiaPlanejamentoRepository.existsById(id)){
            materiaPlanejamentoRepository.deleteById(id);
        }
    }

}
