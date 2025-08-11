package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;
import web.planorama.demo.dto.AdministradorDTO;
import web.planorama.demo.entity.AdministradorEntity;

import java.util.UUID;

@Component
public class UsuarioMapper {

    public AdministradorEntity toAdministradorEntity(AdministradorDTO administradorDTO){
        return new AdministradorEntity(administradorDTO.id(), administradorDTO.nome(), administradorDTO.email(), administradorDTO.senha(), administradorDTO.foto(), administradorDTO.descricao());
    }

    public AdministradorDTO toAdministradorDTO(AdministradorEntity administradorEntity){
        return new AdministradorDTO(administradorEntity.getId(), administradorEntity.getNome(), administradorEntity.getEmail(), administradorEntity.getSenha(), administradorEntity.getFotoUsuario(), administradorEntity.getDescricaoUsuario());
    }
}
