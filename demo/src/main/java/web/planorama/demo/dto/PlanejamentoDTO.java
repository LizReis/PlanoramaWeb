package web.planorama.demo.dto;

import java.util.List;
import java.util.UUID;

public record PlanejamentoDTO(
        UUID idPlanejamento,
        String nomePlanejamento,
        String cargo,
        int anoAplicacao,
        UUID criadorId,
        boolean planoArquivado,
        boolean preDefinidoAdm,
        DisponibilidadeDTO disponibilidade,
        List<MateriaDTO> materias
) {}