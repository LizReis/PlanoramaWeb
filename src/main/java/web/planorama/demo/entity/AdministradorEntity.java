package web.planorama.demo.entity;

import java.util.UUID;

public class AdministradorEntity extends UsuarioEntity{

    public AdministradorEntity(UUID id, String nome, String email, String senha, String foto, String descricao){
        super(id, nome, email, senha, foto, descricao);
    }
}
