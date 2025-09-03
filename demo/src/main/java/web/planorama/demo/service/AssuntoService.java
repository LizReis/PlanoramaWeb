package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.AssuntoDTO;

public interface AssuntoService {
    AssuntoDTO save(AssuntoDTO assuntoDTO);
    AssuntoDTO findById(UUID id);
    List<AssuntoDTO> findAll();
    void remove(UUID id);
}
