package web.planorama.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USUARIO")
public abstract class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "SENHA", nullable = false)
    private String senha;

    @Column(name = "FOTO_USUARIO", nullable = true)
    private String fotoUsuario;

    @Column(name = "DESCRICAO_USUARIO", nullable = true)
    private String descricaoUsuario;

}
