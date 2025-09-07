package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.UsuarioEntity;
import web.planorama.demo.repository.UsuarioRepository;


@Component
@RequiredArgsConstructor
public class UsuarioMapper {
    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity){
        return new UsuarioDTO(usuarioEntity.getId(), usuarioEntity.getNome(), usuarioEntity.getEmail(), usuarioEntity.getSenha(), usuarioEntity.getFotoUsuario(), usuarioEntity.getDescricaoUsuario());
    }

    //Mappeamento manual para o mapSctruct saber como converter em usuarioEntity
    public UsuarioEntity toUsuarioEntity(UsuarioDTO dto) {
        if (dto == null || dto.id() == null) {
            return null;
        }
        // A lógica é buscar o usuário existente no banco de dados pelo ID
        return usuarioRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + dto.id()));
    }

    public EstudanteDTO toEstudanteDTO(EstudanteEntity estudanteEntity){
        return new EstudanteDTO(estudanteEntity.getId(), estudanteEntity.getNome(), estudanteEntity.getEmail(), estudanteEntity.getSenha(), estudanteEntity.getFotoUsuario(), estudanteEntity.getDescricaoUsuario());
    }

    public EstudanteEntity toEstudanteEntity(EstudanteDTO estudanteDTO){
        return new EstudanteEntity(estudanteDTO.id(), estudanteDTO.nome(), estudanteDTO.email(), estudanteDTO.senha(), estudanteDTO.fotoUsuario(), estudanteDTO.descricaoUsuario());
    }

    public AdministradorDTO toAdministradorDTO(AdministradorEntity administradorEntity){
        return new AdministradorDTO(administradorEntity.getId(), administradorEntity.getNome(), administradorEntity.getEmail(), administradorEntity.getSenha(), administradorEntity.getFotoUsuario(), administradorEntity.getDescricaoUsuario());
    }

    public AdministradorEntity toAdministradorEntity(AdministradorDTO administradorDTO){
        return new AdministradorEntity(administradorDTO.id(), administradorDTO.nome(), administradorDTO.email(), administradorDTO.senha(), administradorDTO.fotoUsuario(), administradorDTO.descricaoUsuario());
    }
}
