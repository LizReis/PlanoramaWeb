package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.AssuntoEntity;

public interface MateriaService {
    MateriaDTO save(String nomeMateria, List<AssuntoEntity> listaAssuntos);
    MateriaDTO findById(UUID id);
    List<MateriaDTO> findAll();
    void remove(UUID id);

}
