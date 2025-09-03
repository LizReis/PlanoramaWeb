package web.planorama.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoEstudoDTO {
    private String nomeMateria;
    private int duracaoSessao; // Em minutos
}