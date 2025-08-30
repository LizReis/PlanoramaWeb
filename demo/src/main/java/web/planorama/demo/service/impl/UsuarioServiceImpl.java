package web.planorama.demo.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.AdministradorEntity;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.exceptions.MyNotFoundException;
import web.planorama.demo.mapping.UsuarioMapper;
import web.planorama.demo.repository.UsuarioRepository;
import web.planorama.demo.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO findByEmail(String email) {

        var usuarioEntity = usuarioRepository.findByEmail(email).orElse(null); 
        if (usuarioEntity == null) {
            return null;
        }

        return usuarioMapper.toUsuarioDTO(usuarioEntity);
    }

    @Override
    public UsuarioDTO findOne(UUID id) {
        var entity = usuarioRepository.findById(id).orElseThrow(() -> new MyNotFoundException("Usuário não encontrado."));
        return usuarioMapper.toUsuarioDTO(entity);
    }

    @Override
    public void remove(UUID id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }else{
            throw new MyNotFoundException("Usuário não encontrado para remoção");
        }
    }

    
}