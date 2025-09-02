package web.planorama.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_PLANEJAMENTO")
public class PlanejamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idPlanejamento;

    @Column(name = "NOME_PLANEJAMENTO", nullable = false)
    private String nomePlanejamento;

    @Column(name = "CARGO", nullable = false)
    private String cargo;

    @Column(name = "ANO_APLICACAO", nullable = false)
    private int anoAplicacao;

    @ManyToOne
    @JoinColumn(name = "criador_id") // Nome da coluna de junção
    private UsuarioEntity criador;

    @OneToMany(mappedBy = "planejamento", cascade = CascadeType.ALL)
    private List<MateriaEntity> materias;

    @OneToOne(mappedBy = "planejamento", cascade = CascadeType.ALL)
    private DisponibilidadeEntity disponibilidade;

    @Column(name = "PLANO_ARQUIVADO", nullable = false)
    private boolean planoArquivado = false;

    @Column(name = "PRE_DEFINIDO_ADM")
    private boolean preDefinidoAdm = false;

}