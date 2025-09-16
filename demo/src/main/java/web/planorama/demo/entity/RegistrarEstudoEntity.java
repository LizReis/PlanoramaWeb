package web.planorama.demo.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_REGISTRAR_ESTUDO")
public class RegistrarEstudoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne 
    @JoinColumn(name = "ASSUNTO", nullable = false)
    private AssuntoEntity assunto;

    @Column(name = "DURACAO_MINUTOS" , nullable = false)
    private int duracaoEmMinutos;

    @Column(name = "DATA_REGISTRO" ,nullable = false)
    private LocalDateTime dataRegistro;

    @ManyToOne
    @JoinColumn(name = "USUARIO", nullable = false)
    private UsuarioEntity usuario;
}
