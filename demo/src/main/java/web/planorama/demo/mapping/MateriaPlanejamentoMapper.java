package web.planorama.demo.mapping;


import org.mapstruct.Mapper;

import web.planorama.demo.dto.MateriaPlanejamentoDTO;
import web.planorama.demo.entity.MateriaPlanejamentoEntity;

@Mapper(componentModel = "spring", uses = { UsuarioMapper.class})
public interface MateriaPlanejamentoMapper {
    public MateriaPlanejamentoEntity toMateriaPlanejamentoEntity(MateriaPlanejamentoDTO materiaPlanejamentoDTO);

    public MateriaPlanejamentoDTO toMateriaPlanejamentoDTO(MateriaPlanejamentoEntity materiaPlanejamentoEntity);
}
