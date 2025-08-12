package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.MateriaDTO;

public interface MateriaService {
    MateriaDTO save(MateriaDTO materiaDTO);
    MateriaDTO findOne(UUID id);
    List<MateriaDTO> findAll();
    void remove(UUID id);
}
