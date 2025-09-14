package web.planorama.demo.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class RegistrarEstudoDTO{
    private UUID id;
    private int horasEstudadas;
    private int minutosEstudados;
}
