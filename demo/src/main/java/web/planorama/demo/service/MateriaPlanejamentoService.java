package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.MateriaPlanejamentoDTO;

public interface MateriaPlanejamentoService {
    MateriaPlanejamentoDTO save(int nivelConhecimento, int cargaHorariaMateriaPlano);
    MateriaPlanejamentoDTO findById(UUID id);
    List<MateriaPlanejamentoDTO> findAll();
   
    void remove(UUID id);
}
