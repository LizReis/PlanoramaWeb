package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;
import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;

@Component
public class MateriaPlanejamentoMapper {

    public MateriaPlanejamentoEntity toMateriaPlanejamentoEntity(MateriaPlanejamentoDTO dto) {
        if (dto == null) {
            return null;
        }
        // Agora o Lombok cria o construtor correto e o mapper o utiliza.
        return new MateriaPlanejamentoEntity(
                dto.getId(),
                dto.getPlanejamentoEntity(),
                dto.getMateriaEntity(),
                dto.getNivelConhecimento(),
                dto.getCargaHorariaSemanal(), // Nome do campo corrigido
                dto.getTempoSessao()          // Campo que faltava
        );
    }

    public MateriaPlanejamentoDTO toMateriaPlanejamentoDTO(MateriaPlanejamentoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new MateriaPlanejamentoDTO(
                entity.getId(),
                entity.getPlanejamentoEntity(),
                entity.getMateriaEntity(),
                entity.getNivelConhecimento(),
                entity.getCargaHorariaSemanal(), // Nome do campo corrigido
                entity.getTempoSessao()          // Campo que faltava
        );
    }
}