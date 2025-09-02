package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.dto.UsuarioDTO;
import web.planorama.demo.entity.EstudanteEntity;
import web.planorama.demo.entity.UsuarioEntity;


@Component
public class UsuarioMapper {
    public UsuarioDTO toUsuarioDTO(UsuarioEntity usuarioEntity){
        return new UsuarioDTO(usuarioEntity.getId(), usuarioEntity.getNome(), usuarioEntity.getEmail(), usuarioEntity.getSenha(), usuarioEntity.getFotoUsuario(), usuarioEntity.getDescricaoUsuario());
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
