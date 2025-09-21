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
import lombok.ToString;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CRIADO_POR", nullable = true)
    private UsuarioEntity criadoPor;

    //Uma matéria tem muitos assuntos, exclua todos os assuntos caso a matéria seja excluída
    //carregue todos os assuntos junto com essa matéria (é o que o eager faz)
    @ToString.Exclude
    @OneToMany(mappedBy = "materiaEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AssuntoEntity> listaAssuntos = new ArrayList<>();

    @OneToMany(mappedBy = "materiaEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SessaoEstudoEntity> listaSessao = new ArrayList<>();

    @OneToMany(mappedBy = "materiaEntity")
    private List<MateriaPlanejamentoEntity> planejamentosComMateria = new ArrayList<>();

    @OneToMany(mappedBy = "materia")
    private List<RegistrarEstudoEntity> registrosEstudoMateria = new ArrayList<>();
}
