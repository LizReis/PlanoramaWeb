package web.planorama.demo.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_ASSUNTO")
public class AssuntoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NOME_ASSUNTO", nullable = false)
    private String nomeAssunto;

    //Aqui temos muitas assuntos para uma matéria
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MATERIA_ID", nullable = false)
    private MateriaEntity materiaEntity;

    @OneToMany(mappedBy = "assunto")
    private List<RegistrarEstudoEntity> registrosEstudoAssunto = new ArrayList<>();
}
