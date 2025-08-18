package web.planorama.demo.mapping;

import org.springframework.stereotype.Component;

import web.planorama.demo.dto.MateriaDTO;
import web.planorama.demo.entity.MateriaEntity;

@Component
public class MateriaMapper {

    public MateriaEntity toMateriaEntity(MateriaDTO materiaDTO){
        return new MateriaEntity(materiaDTO.id(), materiaDTO.nomeMateria(), materiaDTO.criadoPor());
    }

    public MateriaDTO toMateriaDTO(MateriaEntity materiaEntity){
        return new MateriaDTO(materiaEntity.getId(), materiaEntity.getNomeMateria(), materiaEntity.getCriadoPor());
    }
}
