package web.planorama.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDTO findByEmail(String email) {

        var usuarioEntity = usuarioRepository.findByEmail(email).orElse(null); 
        if (usuarioEntity == null) {
            return null;
        }

        return usuarioMapper.toUsuarioDTO(usuarioEntity);
    }
}