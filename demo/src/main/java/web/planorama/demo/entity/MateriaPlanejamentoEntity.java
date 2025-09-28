package web.planorama.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_MATERIA_PLANEJAMENTO")
public class MateriaPlanejamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANEJAMENTO_ID")
    private PlanejamentoEntity planejamentoEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MATERIA_ID")
    private MateriaEntity materiaEntity;

    @Column(name = "NIVEL_CONHECIMENTO", nullable = false)
    private int nivelConhecimento;

    @Column(name = "CARGA_HORARIA_MATERIA_PLANO", nullable = false)
    private int cargaHorariaMateriaPlano;

    @OneToMany(mappedBy = "materiaPlanejamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RegistrarEstudoEntity> registrosDeEstudo = new ArrayList<>();

    @OneToMany(mappedBy = "materiaPlanejamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SessaoEstudoEntity> listaSessao = new ArrayList<>();
}
