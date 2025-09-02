package web.planorama.demo.dto;

import java.util.UUID;


public record DisponibilidadeDTO(
        UUID id,
        int horasSegunda,
        int horasTerca,
        int horasQuarta,
        int horasQuinta,
        int horasSexta,
        int horasSabado,
        int horasDomingo
) {}