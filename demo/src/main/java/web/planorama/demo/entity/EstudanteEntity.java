package web.planorama.demo.entity;

import java.util.UUID;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
//O DiscriminatorValue define que quem tiver com o Discriminator ESTUDANTE será ESTUDANTE do sistema
@DiscriminatorValue("ESTUDANTE")
public class EstudanteEntity extends UsuarioEntity{
    
    public EstudanteEntity(UUID id, String nome, String email, String senha, String foto, String descricaoUsuario){
        super(id, nome, email, senha, foto, descricaoUsuario);
        
    }
}
 