package web.planorama.demo.service;

import web.planorama.demo.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO findByEmail(String email);

    
}
