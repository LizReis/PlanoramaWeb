package web.planorama.demo.service;

import web.planorama.demo.dto.AdministradorDTO;

import java.util.List;
import java.util.UUID;

public interface AdministradorService {

    AdministradorDTO save(AdministradorDTO administradorDTO);
    AdministradorDTO findOne(UUID id);
    List<AdministradorDTO> findAll();
    void remove(UUID id);
}
