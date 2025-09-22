package web.planorama.demo.mapping;

import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import web.planorama.demo.dto.RegistrarEstudoDTO;
import web.planorama.demo.entity.AssuntoEntity;
import web.planorama.demo.entity.PlanejamentoEntity;
import web.planorama.demo.entity.RegistrarEstudoEntity;


@Mapper(componentModel = "spring")
public interface RegistrarEstudoMapper {

    //Esse mapper se tornou necessário porque DTO e ENTITY possuem variaveis diferentes
    //Logo, precisamos ensinar ao mapper como fazer a transição de entity para dto e vice-versa

    @Mapping(target = "assunto", source = "assuntoId") // Mapeia o ID para a entidade
    @Mapping(target = "duracaoEmMinutos", expression = "java(registrarEstudoDTO.getHorasEstudadas() * 60 + registrarEstudoDTO.getMinutosEstudados())")
    @Mapping(target = "id", ignore = true) // Ignora o ID para não sobrescrever o gerado pelo banco
    @Mapping(target = "dataRegistro", ignore = true) // Será definido no service
    @Mapping(target = "usuario", ignore = true) // Será definido no service
    public RegistrarEstudoEntity toRegistrarEstudoEntity(RegistrarEstudoDTO registrarEstudoDTO);

    @Mapping(target = "assuntoId", source = "assunto.id")
    @Mapping(target = "horasEstudadas", ignore = true) // Ignoramos para calcular manualmente
    @Mapping(target = "minutosEstudados", ignore = true) // Ignoramos para calcular manualmente
    @Mapping(target = "planejamentoId", ignore = true) // A entidade não tem essa informação
    public RegistrarEstudoDTO toRegistrarEstudoDTO(RegistrarEstudoEntity registrarEstudoEntity);


    default AssuntoEntity mapAssuntoIdToAssuntoEntity(UUID assuntoId) {
        if (assuntoId == null) {
            return null;
        }
        AssuntoEntity assunto = new AssuntoEntity();
        assunto.setId(assuntoId);
        return assunto;
    }

    default PlanejamentoEntity mapPlanejamentoIdToPlanejamentoEntity(UUID planejamentoId) {
        if (planejamentoId == null) {
            return null;
        }
        PlanejamentoEntity planejamento = new PlanejamentoEntity();
        planejamento.setId(planejamentoId);
        return planejamento;
    }

    @AfterMapping
    default void calcularHorasEMinutos(@MappingTarget RegistrarEstudoDTO dto, RegistrarEstudoEntity entity) {
        if (entity != null) {
            int duracaoTotal = entity.getDuracaoEmMinutos();
            dto.setHorasEstudadas(duracaoTotal / 60);
            dto.setMinutosEstudados(duracaoTotal % 60);
        }
    }
}
