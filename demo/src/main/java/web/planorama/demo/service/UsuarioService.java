package web.planorama.demo.service;

import java.util.UUID;

import web.planorama.demo.dto.UsuarioDTO;

public interface UsuarioService {
    
    UsuarioDTO findOne(UUID id);
    UsuarioDTO findByEmail(String email);
    void remove(UUID id);
}
