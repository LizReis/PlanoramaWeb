package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;
import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.MateriaEntity;

@Component
public class MateriaMapper {

    //Converte um MateriaDTO para uma MateriaEntity.

    public MateriaEntity toMateriaEntity(MateriaDTO dto) {
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

    //Converte uma MateriaEntity para um MateriaDTO.

    public MateriaDTO toMateriaDTO(MateriaEntity entity) {
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
}