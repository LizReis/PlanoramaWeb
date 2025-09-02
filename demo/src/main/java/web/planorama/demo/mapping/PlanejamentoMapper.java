package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;
import web.planorama.demo.dto.DisponibilidadeDTO;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.dto.PlanejamentoDTO;
import web.planorama.demo.entity.DisponibilidadeEntity;
import web.planorama.demo.entity.MateriaEntity;
import web.planorama.demo.entity.PlanejamentoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanejamentoMapper {

    // Converte um PlanejamentoDTO para uma PlanejamentoEntity.

    public PlanejamentoEntity toPlanejamentoEntity(PlanejamentoDTO dto) {
        if (dto == null) {
            return null;
        }

        PlanejamentoEntity entity = new PlanejamentoEntity();
        entity.setIdPlanejamento(dto.idPlanejamento());
        entity.setNomePlanejamento(dto.nomePlanejamento());
        entity.setCargo(dto.cargo());
        entity.setAnoAplicacao(dto.anoAplicacao());
        entity.setPlanoArquivado(dto.planoArquivado());
        entity.setPreDefinidoAdm(dto.preDefinidoAdm());

        // Usa os métodos auxiliares para converter os objetos aninhados
        entity.setDisponibilidade(disponibilidadeDTOToEntity(dto.disponibilidade()));
        entity.setMaterias(materiaDTOListToEntityList(dto.materias()));

        return entity;
    }

    // Converte uma PlanejamentoEntity para um PlanejamentoDTO.

    public PlanejamentoDTO toPlanejamentoDTO(PlanejamentoEntity entity) {
        if (entity == null) {
            return null;
        }

        // Extrai o ID do criador
        var criadorId = (entity.getCriador() != null) ? entity.getCriador().getId() : null;

        return new PlanejamentoDTO(
                entity.getIdPlanejamento(),
                entity.getNomePlanejamento(),
                entity.getCargo(),
                entity.getAnoAplicacao(),
                criadorId,
                entity.isPlanoArquivado(),
                entity.isPreDefinidoAdm(),
                // Usa os métodos auxiliares para converter os objetos aninhados
                disponibilidadeEntityToDTO(entity.getDisponibilidade()),
                materiaEntityListToDTOList(entity.getMaterias())
        );
    }


    //Metodos Auxiliares para Disponibilidade

    public DisponibilidadeEntity disponibilidadeDTOToEntity(DisponibilidadeDTO dto) {
        if (dto == null) {
            return null;
        }
        DisponibilidadeEntity entity = new DisponibilidadeEntity();
        entity.setId(dto.id());
        entity.setHorasSegunda(dto.horasSegunda());
        entity.setHorasTerca(dto.horasTerca());
        entity.setHorasQuarta(dto.horasQuarta());
        entity.setHorasQuinta(dto.horasQuinta());
        entity.setHorasSexta(dto.horasSexta());
        entity.setHorasSabado(dto.horasSabado());
        entity.setHorasDomingo(dto.horasDomingo());
        return entity;
    }

    public DisponibilidadeDTO disponibilidadeEntityToDTO(DisponibilidadeEntity entity) {
        if (entity == null) {
            return null;
        }
        return new DisponibilidadeDTO(
                entity.getId(),
                entity.getHorasSegunda(),
                entity.getHorasTerca(),
                entity.getHorasQuarta(),
                entity.getHorasQuinta(),
                entity.getHorasSexta(),
                entity.getHorasSabado(),
                entity.getHorasDomingo()
        );
    }


    // Metodos Auxiliares para Materia

    public MateriaEntity materiaToEntity(MateriaDTO dto) {
        if (dto == null) {
            return null;
        }
        MateriaEntity entity = new MateriaEntity();
        entity.setId(dto.id());
        entity.setNomeMateria(dto.nomeMateria());
        entity.setCargaHorariaSemanal(dto.cargaHorariaSemanal());
        entity.setProficiencia(dto.proficiencia());
        entity.setTempoSessao(dto.tempoSessao());
        return entity;
    }

    public MateriaDTO materiaToDTO(MateriaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new MateriaDTO(
                entity.getId(),
                entity.getNomeMateria(),
                entity.getCargaHorariaSemanal(),
                entity.getProficiencia(),
                entity.getTempoSessao()
        );
    }

    public List<MateriaEntity> materiaDTOListToEntityList(List<MateriaDTO> dtoList) {
        if (dtoList == null) {
            return new ArrayList<>();
        }
        return dtoList.stream()
                .map(this::materiaToEntity)
                .collect(Collectors.toList());
    }

    public List<MateriaDTO> materiaEntityListToDTOList(List<MateriaEntity> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream()
                .map(this::materiaToDTO)
                .collect(Collectors.toList());
    }
}