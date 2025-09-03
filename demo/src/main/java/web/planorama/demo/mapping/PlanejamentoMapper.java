package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.PlanejamentoEntity;

@Component
public class PlanejamentoMapper {

    public PlanejamentoEntity toPlanejamentoEntity(PlanejamentoDTO planejamentoDTO){
        return new PlanejamentoEntity(planejamentoDTO.getId(), planejamentoDTO.getNomePlanejamento(), planejamentoDTO.getCargo(), planejamentoDTO.getAnoAplicacao(), planejamentoDTO.getDisponibilidade(), planejamentoDTO.getHorasDiarias(), planejamentoDTO.getMaterias(), planejamentoDTO.getCriador(), planejamentoDTO.isPlanoArquivado(), planejamentoDTO.isPreDefinidoAdm());
    }

    public PlanejamentoDTO toPlanejamentoDTO(PlanejamentoEntity planejamentoEntity){
        return new PlanejamentoDTO(planejamentoEntity.getIdPlanejamento(), planejamentoEntity.getNomePlanejamento(), planejamentoEntity.getCargo(), planejamentoEntity.getAnoAplicacao(), planejamentoEntity.getDisponibilidade(), planejamentoEntity.getHorasDiarias(), planejamentoEntity.getMaterias(), planejamentoEntity.getCriador(), planejamentoEntity.isPlanoArquivado(), planejamentoEntity.isPreDefinidoAdm());
    }
}
