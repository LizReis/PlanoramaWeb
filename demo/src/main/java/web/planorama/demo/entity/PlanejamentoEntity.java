package web.planorama.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private UUID id;

    @Column(name = "NOME_PLANEJAMENTO", nullable = false)
    private String nomePlanejamento;

    @Column(name = "CARGO", nullable = false)
    private String cargo;

    @Column(name = "ANO_APLICACAO", nullable = false)
    private int anoAplicacao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TB_PLANEJAMENTO_DISPONIBILIDADE", 
    joinColumns = @JoinColumn(name = "PLANEJAMENTO_ID"))
    @Column(name = "DISPONIBILIDADE", nullable = false)
    private List<String> disponibilidade = new ArrayList<>();

    @Column(name = "HORAS_DIARIAS", nullable = false)
    private int horasDiarias;

    //Quando salvarmos, editarmos ou deletarmos um Planejamento todas as matérias ligadas a ele
    //Serão alteradas juntamente
    @OneToMany(mappedBy = "planejamentoEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MateriaPlanejamentoEntity> materias = new ArrayList<>();

    @OneToMany(mappedBy = "planejamentoEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SessaoEstudoEntity> sessoesEstudo = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "Criador")
    private UsuarioEntity criador;

    @Column(name = "PLANO_ARQUIVADO", nullable = false)
    private boolean planoArquivado;

    @Column(name = "PRE_DEFINIDO_ADM", nullable = true)
    private boolean preDefinidoAdm;

}
