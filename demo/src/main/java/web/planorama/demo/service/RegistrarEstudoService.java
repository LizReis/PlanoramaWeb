package web.planorama.demo.service;

import java.util.List;
import java.util.UUID;

import web.planorama.demo.dto.RegistrarEstudoDTO;

public interface RegistrarEstudo{
    RegistrarEstudoDTO save(RegistrarEstudoDTO registrarEstudoDTO);
    RegistrarEstudoDTO findById(UUID id);
    List<RegistrarEstudoDTO> findAll(); //pode ser usado para mostrar em um gráfico de desempenho de Registro de Estudo
    void remove(UUID id);
}