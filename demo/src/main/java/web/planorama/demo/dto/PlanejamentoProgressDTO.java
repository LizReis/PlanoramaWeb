package web.planorama.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanejamentoProgressDTO {

    private PlanejamentoDTO planejamento;
    private int progressPercentage;
    private boolean concluido;
}
