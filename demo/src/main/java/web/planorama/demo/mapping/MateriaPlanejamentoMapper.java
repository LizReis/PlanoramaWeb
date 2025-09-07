package web.planorama.demo.mapping;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class})
public interface MateriaPlanejamentoMapper {
    @Mapping(target = "nivelConhecimento", defaultValue = "0")
    @Mapping(target = "cargaHorariaMateriaPlano", defaultValue = "0")
    public MateriaPlanejamentoEntity toMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO);

    @Mapping(target = "planejamentoDTO", ignore = true) 
    public MateriaPlanejamentoDTO toMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity);
}
