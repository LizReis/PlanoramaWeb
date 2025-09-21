package web.planorama.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_USUARIO")
public class UsuarioEntity {

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

    @OneToMany(mappedBy = "criador")
    private List<PlanejamentoEntity> planejamentos;

    @OneToMany(mappedBy = "usuario")
    private List<RegistrarEstudoEntity> registrosEstudoUsuario = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USUARIO_PAPEL",
    joinColumns = {
      @JoinColumn(name = "USUARIO_ID")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "PAPEL_ID")
    })
    private List<PapelEntity> papeis;

}
