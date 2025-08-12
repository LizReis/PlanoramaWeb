package web.planorama.demo.service;

import web.planorama.demo.dto.EstudanteDTO;

import java.util.List;
import java.util.UUID;


public interface EstudanteService {

    EstudanteDTO save(EstudanteDTO estudanteDTO);
    EstudanteDTO findOne(UUID id);
    List<EstudanteDTO> findAll();
    void remove(UUID id);
}
