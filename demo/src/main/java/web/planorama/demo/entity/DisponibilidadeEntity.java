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
@Table(name = "TB_DISPONIBILIDADE")
public class DisponibilidadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "HORAS_SEGUNDA", nullable = false)
    private int horasSegunda = 0;

    @Column(name = "HORAS_TERCA", nullable = false)
    private int horasTerca = 0;

    @Column(name = "HORAS_QUARTA", nullable = false)
    private int horasQuarta = 0;

    @Column(name = "HORAS_QUINTA", nullable = false)
    private int horasQuinta = 0;

    @Column(name = "HORAS_SEXTA", nullable = false)
    private int horasSexta = 0;

    @Column(name = "HORAS_SABADO", nullable = false)
    private int horasSabado = 0;

    @Column(name = "HORAS_DOMINGO", nullable = false)
    private int horasDomingo = 0;

    @OneToOne
    @JoinColumn(name = "planejamento_id")
    private PlanejamentoEntity planejamento;
}