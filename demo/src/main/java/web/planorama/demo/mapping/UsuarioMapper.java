package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import web.planorama.demo.dto.EstudanteDTO;
import web.planorama.demo.entity.EstudanteEntity;

@Component
public class UsuarioMapper {
//-----------------------------MAPPER PARA O USUÁRIO ADMINISTRADOR----------------------------------------------
    public AdministradorEntity toAdministradorEntity(AdministradorDTO administradorDTO){
        return new AdministradorEntity(administradorDTO.id(), administradorDTO.nome(), administradorDTO.email(), administradorDTO.senha(), administradorDTO.fotoUsuario(), administradorDTO.descricaoUsuario());
    }

    public AdministradorDTO toAdministradorDTO(AdministradorEntity administradorEntity){
        return new AdministradorDTO(administradorEntity.getId(), administradorEntity.getNome(), administradorEntity.getEmail(), administradorEntity.getSenha(), administradorEntity.getFotoUsuario(), administradorEntity.getDescricaoUsuario());
    }

//-----------------------------MAPPER PARA O USUÁRIO ESTUDANTE----------------------------------------------
    public EstudanteEntity toEstudanteEntity(EstudanteDTO estudanteDTO){
        return new EstudanteEntity(estudanteDTO.id(), estudanteDTO.nome(), estudanteDTO.email(), estudanteDTO.senha(), estudanteDTO.fotoUsuario(), estudanteDTO.descricaoUsuario());
    }

    public EstudanteDTO toEstudanteDTO(EstudanteEntity estudanteEntity){
        return new EstudanteDTO(estudanteEntity.getId(), estudanteEntity.getNome(), estudanteEntity.getEmail(), estudanteEntity.getSenha(), estudanteEntity.getFotoUsuario(), estudanteEntity.getDescricaoUsuario());
    }
}
