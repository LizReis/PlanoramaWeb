package web.planorama.demo.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.planorama.demo.entity.MateriaEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoEstudoDTO {
    private UUID id;
    private MateriaEntity materiaEntity;
    private int duracaoSessao; // Em minutos
}