package web.planorama.demo.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Column(name = "DISPONILIBILIDADE", nullable = false)
    private ArrayList<String> disponibilidade;

    //Inicialmente materias como string porque ainda não criei a classe MateriaPlano
    @Column(name = "MATERIAS", nullable = false)
    private ArrayList<String> materias;

    @Column(name = "USUARIO", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "PLANO_ARQUIVADO", nullable = false)
    private boolean planoArquivado;

    @Column(name = "PRE_DEFINIDO_ADM", nullable = true)
    private boolean preDefinidoAdm;

}
