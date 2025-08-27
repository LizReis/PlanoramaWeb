package web.planorama.demo.entity;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
//O DiscriminatorValue define que quem tiver com o Discriminator ADMIN será o ADMIN do sistema
@DiscriminatorValue("ADMIN")
public class AdministradorEntity extends UsuarioEntity{

    public AdministradorEntity(UUID id, String nome, String email, String senha, String foto, String descricaoUsuario){
        super(id, nome, email, senha, foto, descricaoUsuario);
    }
}
