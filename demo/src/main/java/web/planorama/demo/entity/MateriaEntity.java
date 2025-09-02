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
@Table(name = "TB_MATERIA")
public class MateriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NOME_MATERIA", nullable = false)
    private String nomeMateria;

    @Column(name = "CARGA_HORARIA_SEMANAL", nullable = false)
    private int cargaHorariaSemanal;

    @Column(name = "PROFICIENCIA", nullable = false)
    private int proficiencia; // De 1 a 5

    @Column(name = "TEMPO_SESSAO", nullable = false)
    private int tempoSessao; // Em minutos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planejamento_id")
    private PlanejamentoEntity planejamento;
}