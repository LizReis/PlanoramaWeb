package web.planorama.demo.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoEstudoDTO {
    private UUID id;
    private MateriaDTO materiaDTO;
    private int duracaoSessao; // Em minutos
}